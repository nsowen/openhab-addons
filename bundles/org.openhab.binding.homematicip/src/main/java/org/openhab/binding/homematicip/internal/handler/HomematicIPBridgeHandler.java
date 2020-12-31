package org.openhab.binding.homematicip.internal.handler;

import static org.openhab.binding.homematicip.internal.HomematicIPBindingConstants.THING_TYPE_BRIDGE;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.homematicip.internal.*;
import org.openhab.binding.homematicip.internal.discovery.HomematicIPDiscoveryService;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.event.StateChange;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.home.Home;
import org.openhab.binding.homematicip.internal.transport.HttpTransport;
import org.openhab.core.config.core.Configuration;
import org.openhab.core.config.core.status.ConfigStatusMessage;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.io.net.http.WebSocketFactory;
import org.openhab.core.thing.*;
import org.openhab.core.thing.binding.ConfigStatusBridgeHandler;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Homematic IP Bridge handler
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-26
 */
public class HomematicIPBridgeHandler extends ConfigStatusBridgeHandler
        implements HomematicIPClient, HomematicIPEventListener {

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_BRIDGE);
    private final static int PAIRING_ATTEMPT_MAX = 10;
    private final static int PAIRING_ATTEMPT_DELAY_MILLIS = 3000;
    private final Logger logger = LoggerFactory.getLogger(HomematicIPBridgeHandler.class);
    private final HttpClientFactory httpClientFactory;
    private final WebSocketFactory webSocketFactory;

    private HomematicIPConnection connection;
    private HomematicIPConfiguration bridgeConfig;

    private final ReentrantLock updateLock = new ReentrantLock();

    private final Map<String, Device> lastDevices = new ConcurrentHashMap<>();
    private final Map<String, Group> lastGroups = new ConcurrentHashMap<>();
    private final AtomicReference<Home> lastHome = new AtomicReference<>();
    private @Nullable HomematicIPDiscoveryService discoveryService;

    private Map<String, ThingStatusListener> thingStatusListeners = new ConcurrentHashMap<>();

    public HomematicIPBridgeHandler(Bridge bridge, HttpClientFactory httpClientFactory,
            WebSocketFactory webSocketFactory) {
        super(bridge);
        this.httpClientFactory = httpClientFactory;
        this.webSocketFactory = webSocketFactory;
    }

    @Override
    public void initialize() {
        logger.trace("Initializing bridge");
        bridgeConfig = getConfigAs(HomematicIPConfiguration.class);
        updateStatus(ThingStatus.UNKNOWN);
        if (StringUtils.isNotEmpty(bridgeConfig.getAccessPointId())) {
            connection = createConnection();
            connection.initializeAsync(scheduler).thenAcceptAsync((c) -> checkPairingStatus(), scheduler);
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "@text/offline.conf-error-no-accesspoint-id");
        }
    }

    /**
     * Checks the connection's pairing status
     */
    private void checkPairingStatus() {
        if (connection.isReadyForPairing()) {
            if (executePairing()) {
                logger.debug("Pairing successful: " + connection);
                onUpdate();
            } else {
                logger.warn("Pairing failed for connection: " + connection);
            }
        } else if (connection.isReadyForUse()) {
            logger.debug("Got valid connection: " + connection);
            updateStatus(ThingStatus.ONLINE);
            onUpdate();
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "@text/offline.communication-error");
        }
    }

    /**
     * Called whenever where are online a need to fetch new data
     */
    private void onUpdate() {
        if (!connection.isReadyForUse()) {
            logger.warn("onUpdate() called without valid connection");
            return;
        }
        connection.loadCurrentState(scheduler).thenAcceptAsync((response) -> {
            updateLock.lock();
            try {
                logger.trace("Update received, triggering listeners");
                updateDevices(response.getDevices());
                updateGroups(response.getGroups());
                updateHome(response.getHome());
                enableEventListener();
                logger.trace("Done triggering listeners");
            } finally {
                updateLock.unlock();
            }
            discoveryService.startScan();
        }, scheduler);
        logger.debug("Update call done");
    }

    private void enableEventListener() {
        try {
            connection.enableEventListener(this);
        } catch (IOException e) {
            logger.error("Cannot enable event listener: {}", e.getMessage(), e);
        }
    }

    private void disableEventListener() {
        connection.disableEventListener();
    }

    /**
     * Check last state for devices being added, changed, or removed,
     * and notify ThingHandlers accordingly
     *
     * @param devices map of id->devices
     */
    private void updateDevices(Map<String, Device> devices) {
        var lastDevicesCopy = new HashMap<>(lastDevices);
        devices.forEach((id, device) -> {
            var listener = thingStatusListeners.get(id);
            if (listener == null) { // not yet added as thing
                logger.trace("Homematic IP device '{}' added", device.getId());
                if (discoveryService != null && !lastDevices.containsKey(id)) {
                    discoveryService.addDeviceDiscovery(device);
                }
                lastDevices.put(id, device);
            } else {
                if (listener.onStateChanged(device)) {
                    lastDevices.put(id, device);
                }
            }
            lastDevicesCopy.remove(id);
        });
        // check for removed devices and notify
        lastDevicesCopy.forEach((id, device) -> {
            logger.trace("Homematic IP device '{}' removed", device.getId());
            lastDevices.remove(id);
            var listener = thingStatusListeners.get(id);
            if (listener != null) {
                listener.onRemoved();
            }
            if (discoveryService != null) {
                discoveryService.removeDiscovery(device);
            }
        });
    }

    /**
     * Check last state for groups being added, changed, or removed,
     * and notify ThingHandlers accordingly
     *
     * @param groups map of id->group
     */
    private void updateGroups(Map<String, Group> groups) {
        var lastGroupsCopy = new HashMap<>(lastGroups);
        groups.forEach((id, group) -> {
            var listener = thingStatusListeners.get(id);
            if (listener == null) { // not yet added as thing
                logger.trace("Homematic IP group '{}' added", group.getId());
                if (discoveryService != null && !lastGroups.containsKey(id)) {
                    discoveryService.addGroupDiscovery(group);
                }
                lastGroups.put(id, group);
            } else {
                if (listener.onStateChanged(group)) {
                    lastGroups.put(id, group);
                }
            }
            lastGroupsCopy.remove(id);
        });
        // check for removed devices and notify
        lastGroupsCopy.forEach((id, group) -> {
            logger.trace("Homematic IP group '{}' removed", group.getId());
            lastGroups.remove(id);
            var listener = thingStatusListeners.get(id);
            if (listener != null) {
                listener.onRemoved();
            }
            if (discoveryService != null) {
                discoveryService.removeDiscovery(group);
            }
        });
    }

    /**
     * Check last state for home being changed
     * and notify ThingHandlers accordingly
     *
     * @param home new home
     */
    private void updateHome(Home home) {
        var lastHomeCopy = lastHome.get();
        if (lastHomeCopy != home) {
            var listener = thingStatusListeners.get(home.getHomeId());
            if (listener == null) {
                logger.trace("Homematic IP home '{}' added", home.getId());
                if (discoveryService != null) {
                    discoveryService.addHomeDiscovery(home);
                }
                lastHome.set(home);
            } else {
                logger.trace("Homematic IP home '{}' updated", home.getId());
                if (listener.onStateChanged(home)) {
                    lastHome.set(home);
                }
            }
        }
    }

    /**
     * Get current groups
     *
     * @return
     */
    public Collection<Group> getGroups() {
        return lastGroups.values();
    }

    @Override
    public Optional<Group> getGroupById(String id) {
        return Optional.ofNullable(lastGroups.get(id));
    }

    /**
     * Get current groups
     *
     * @return
     */
    public Collection<Device> getDevices() {
        return lastDevices.values();
    }

    @Override
    public Optional<Device> getDeviceById(String id) {
        return Optional.ofNullable(lastDevices.get(id));
    }

    @Override
    public Optional<Home> getHome() {
        return Optional.ofNullable(lastHome.get());
    }

    /**
     * Test if the bridge is ready for use (state received, connection is up)
     *
     * @return
     */
    public boolean isReadyForUse() {
        return connection != null && connection.isReadyForUse() && lastHome.get() != null;
    }

    /**
     * Trigger pairing of new access point by working the Homematic IP APIs and wait
     * for haptic feedback by pressing the link button.
     *
     * @return true if pairing was successful
     */
    private boolean executePairing() {
        try {
            connection.authConnectionRequest();
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "@text/offline.conf-error-press-pairing-button");
            for (var attempt = 0; attempt < PAIRING_ATTEMPT_MAX
                    && !connection.authIsRequestAcknowledgedRequest(); attempt++) {
                Thread.sleep(PAIRING_ATTEMPT_DELAY_MILLIS);
                if (attempt == PAIRING_ATTEMPT_MAX) {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                            "@text/offline.communication-error");
                    logger.warn("Cannot initialize Homematic IP: {}", "link button was not pressed in time");
                    return false;
                }
            }
            final var tokenResponse = connection.authRequestToken();
            updateBridgeThingConfigurationAuthToken(tokenResponse.getAuthToken());
            try {
                connection.authConfirmToken(tokenResponse.getAuthToken());
                connection.setAuthToken(tokenResponse.getAuthToken());
                updateStatus(ThingStatus.ONLINE);
                return true;
            } catch (Exception e) {
                logger.warn("Cannot initialize Homematic IP: {}", e.getMessage(), e);
                updateBridgeThingConfigurationAuthToken(null); // reset auth token in case of confirm error
            }
        } catch (IOException | InterruptedException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "@text/offline.communication-error");
            logger.warn("Cannot initialize Homematic IP: {}", e.getMessage(), e);
        }
        return false;
    }

    private void updateBridgeThingConfigurationAuthToken(String authToken) {
        Configuration config = editConfiguration();
        config.put(HomematicIPBindingConstants.AUTH_TOKEN, authToken);
        try {
            updateConfiguration(config);
            logger.debug("Updated configuration parameter '{}'", authToken);
            bridgeConfig = getConfigAs(HomematicIPConfiguration.class);
        } catch (IllegalStateException e) {
            logger.trace("Configuration update failed.", e);
            logger.warn("Unable to update configuration of Hue bridge.");
            logger.warn("Please configure the user name manually.");
        }
    }

    @Override
    public boolean registerDiscoveryListener(HomematicIPDiscoveryService listener) {
        if (discoveryService == null) {
            discoveryService = listener;
            getDevices().forEach(listener::addDeviceDiscovery);
            getGroups().forEach(listener::addGroupDiscovery);
            getHome().ifPresent(listener::addHomeDiscovery);
            return true;
        }
        return false;
    }

    @Override
    public boolean unregisterDiscoveryListener() {
        if (discoveryService != null) {
            discoveryService = null;
            return true;
        }
        return false;
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
    }

    private HomematicIPConnection createConnection() {
        var transport = new HttpTransport(httpClientFactory, webSocketFactory, scheduler);
        var connection = new HomematicIPConnection(getThing().getUID().getAsString(), bridgeConfig.getAccessPointId(),
                bridgeConfig.getAuthToken(), transport);
        return connection;
    }

    @Override
    public Collection<ConfigStatusMessage> getConfigStatus() {
        return Collections.emptySet();
    }

    @Override
    public boolean registerThingStatusListener(ThingStatusListener listener) {
        final String id = listener.getId();
        if (!thingStatusListeners.containsKey(id)) {
            thingStatusListeners.put(id, listener);
            return true;
        }
        return false;
    }

    @Override
    public boolean unregisterThingStatusListener(ThingStatusListener lightStatusListener) {
        return thingStatusListeners.remove(lightStatusListener.getId()) != null;
    }

    @Override
    public void onReceive(StateChange stateChange) {
        logger.debug("Received event data: {}", stateChange);
    }
}
