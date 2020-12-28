package org.openhab.binding.homematicip.internal;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.openhab.binding.homematicip.internal.model.transport.HttpTransport;
import org.openhab.core.config.core.status.ConfigStatusMessage;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.io.net.http.WebSocketFactory;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
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
                        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                                "@text/offline.conf-error-press-pairing-button");
                    } else if (connection.isReadyForUse()) {
                        logger.debug("Got valid connection: " + connection);
                        updateStatus(ThingStatus.ONLINE);
                    } else {
                        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                                "@text/offline.communication-error");
                    }
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
