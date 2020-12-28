package org.openhab.binding.homematicip.internal.handler;

import static org.openhab.binding.homematicip.internal.HomematicIPBindingConstants.THING_TYPE_BRIDGE;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.openhab.binding.homematicip.internal.HomematicIPBindingConstants;
import org.openhab.binding.homematicip.internal.HomematicIPConfiguration;
import org.openhab.binding.homematicip.internal.HomematicIPConnection;
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
public class HomematicIPBridgeHandler extends ConfigStatusBridgeHandler {

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_BRIDGE);
    private final static int PAIRING_ATTEMPT_MAX = 10;
    private final static int PAIRING_ATTEMPT_DELAY_MILLIS = 3000;
    private final Logger logger = LoggerFactory.getLogger(HomematicIPBridgeHandler.class);
    private final HttpClientFactory httpClientFactory;
    private final WebSocketFactory webSocketFactory;
    private HomematicIPConnection connection;
    private HomematicIPConfiguration bridgeConfig;

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
            try {
                this.connection = createConnection();
                this.connection.initAsync(scheduler).thenAccept((c) -> {
                    if (connection.isReadyForPairing()) {
                        executePairing();
                    } else if (connection.isReadyForUse()) {
                        logger.debug("Got valid connection: " + connection);
                        updateStatus(ThingStatus.ONLINE);
                    } else {
                        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                                "@text/offline.communication-error");
                    }
                    // TODO: start discovery if we are online
                });
            } catch (IOException | NoSuchAlgorithmException e) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                        "@text/offline.communication-error");
                logger.warn("Cannot initialize Homematic IP: {}", e.getMessage(), e);
            }
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "@text/offline.conf-error-no-accesspoint-id");
        }
    }

    /**
     * Trigger pairing of new access point by working the Homematic IP APIs and wait
     * for haptic feedback by pressing the link button.
     */
    private void executePairing() {
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
                    return;
                }
            }
            final var tokenResponse = connection.authRequestToken();
            updateBridgeThingConfigurationAuthToken(tokenResponse.getAuthToken());
            try {
                connection.authConfirmToken(tokenResponse.getAuthToken());
                connection.setAuthToken(tokenResponse.getAuthToken());
                logger.debug("Pairing successful: " + connection);
                updateStatus(ThingStatus.ONLINE);
            } catch (Exception e) {
                logger.warn("Cannot initialize Homematic IP: {}", e.getMessage(), e);
                updateBridgeThingConfigurationAuthToken(null); // reset auth token in case of confirm error
            }
        } catch (IOException | InterruptedException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "@text/offline.communication-error");
            logger.warn("Cannot initialize Homematic IP: {}", e.getMessage(), e);
        }
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
    public void handleCommand(ChannelUID channelUID, Command command) {
    }

    private HomematicIPConnection createConnection() throws IOException, NoSuchAlgorithmException {
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
