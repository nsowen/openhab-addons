package org.openhab.binding.homematicip.internal;

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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;

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

    public HomematicIPBridgeHandler(Bridge bridge, HttpClientFactory httpClientFactory, WebSocketFactory webSocketFactory) {
        super(bridge);
        this.httpClientFactory = httpClientFactory;
        this.webSocketFactory = webSocketFactory;
    }

    @Override
    public void initialize() {
        updateStatus(ThingStatus.UNKNOWN);
        try {
            this.connection = createConnection();
            this.connection.initAsync(scheduler)
                    .thenAccept((c) -> {
                        // todo check if all is set
                        updateStatus(ThingStatus.ONLINE);
                    });
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
            logger.warn("Cannot initialize Homematic IP: {}", e.getMessage(), e);
        }

    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

    }

    private HomematicIPConnection createConnection() throws IOException, NoSuchAlgorithmException {
        var accessPointId = (String) getThing().getConfiguration().get("accessPointId");
        var transport = new HttpTransport(httpClientFactory, webSocketFactory);
        var connection = new HomematicIPConnection(getThing().getUID().getAsString(), accessPointId, transport);
        return connection;
    }

    @Override
    public Collection<ConfigStatusMessage> getConfigStatus() {
        return Collections.emptySet();
    }
}
