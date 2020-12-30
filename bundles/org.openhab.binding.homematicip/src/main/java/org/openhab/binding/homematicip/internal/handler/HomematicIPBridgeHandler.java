package org.openhab.binding.homematicip.internal.handler;

import static org.openhab.binding.homematicip.internal.HomematicIPBindingConstants.THING_TYPE_BRIDGE;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.homematicip.internal.HomematicIPBindingConstants;
import org.openhab.binding.homematicip.internal.HomematicIPClient;
import org.openhab.binding.homematicip.internal.HomematicIPConfiguration;
import org.openhab.binding.homematicip.internal.HomematicIPConnection;
import org.openhab.binding.homematicip.internal.discovery.HomematicIPDiscoveryService;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.home.Home;
import org.openhab.binding.homematicip.internal.model.response.GetCurrentStateResponse;
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
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-26
 */
public class HomematicIPBridgeHandler extends ConfigStatusBridgeHandler implements HomematicIPClient {

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_BRIDGE);
    private final static int PAIRING_ATTEMPT_MAX = 10;
    private final static int PAIRING_ATTEMPT_DELAY_MILLIS = 3000;
    private final Logger logger = LoggerFactory.getLogger(HomematicIPBridgeHandler.class);
    private final HttpClientFactory httpClientFactory;
    private final WebSocketFactory webSocketFactory;
    private HomematicIPConnection connection;
    private HomematicIPConfiguration bridgeConfig;
    private @Nullable volatile GetCurrentStateResponse state;

    private @Nullable HomematicIPDiscoveryService discoveryService;

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
            logger.debug("State done");
            synchronized (this) {
                state = response;
            }
            logger.debug("Starting scan");
            discoveryService.startScan();
        }, scheduler);
        logger.debug("Update call done");
    }

    /**
     * Get current groups
     *
     * @return
     */
    public List<Group> getGroups() {
        return state != null ? state.getGroupList() : Collections.emptyList();
    }

    /**
     * Get current groups
     *
     * @return
     */
    public List<Device> getDevices() {
        return state != null ? state.getDeviceList() : Collections.emptyList();
    }

    public GetCurrentStateResponse getCurrentStateResponse() {
        return state;
    }

    /**
     * Test if the bridge is ready for use (state received, connection is up)
     *
     * @return
     */
    public boolean isReadyForUse() {
        return connection != null && connection.isReadyForUse() && state != null;
    }

    /**
     * Get current groups
     *
     * @return
     */
    public Home getHome() {
        return state != null ? state.getHome() : null;
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
        var transport = new HttpTransport(httpClientFactory, webSocketFactory);
        var connection = new HomematicIPConnection(getThing().getUID().getAsString(), bridgeConfig.getAccessPointId(),
                bridgeConfig.getAuthToken(), transport);
        return connection;
    }

    @Override
    public Collection<ConfigStatusMessage> getConfigStatus() {
        return Collections.emptySet();
    }
}
