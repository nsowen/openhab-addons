package org.openhab.binding.homematicip.internal.transport;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.extensions.Frame;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.eclipse.jetty.websocket.common.frames.PongFrame;
import org.openhab.core.io.net.http.WebSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The {@link HomematicIPWebSocket} implements the websocket for receiving constant updates from the Homematic IP
 * cloud.
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-31
 */
@NonNullByDefault
@WebSocket
public class HomematicIPWebSocket {

    private final Logger logger = LoggerFactory.getLogger(HomematicIPWebSocket.class);
    private final WebSocketListener socketEventListener;
    private final long WEBSOCKET_IDLE_TIMEOUT = 300;

    private WebSocketSession session;
    private WebSocketClient webSocketClient;
    private boolean closing;
    private Instant lastPong = Instant.now();
    private ScheduledExecutorService scheduler;
    private @Nullable ScheduledFuture<?> connectionTracker;
    private ByteBuffer pingPayload = ByteBuffer.wrap("ping".getBytes(StandardCharsets.UTF_8));
    private String socketId;

    public HomematicIPWebSocket(WebSocketListener socketEventListener,
                                 ScheduledExecutorService scheduler,
                                 String websocketUrl,
                                 WebSocketFactory webSocketFactory, String socketId) throws Exception {
        this.socketEventListener = socketEventListener;
        this.scheduler = scheduler;
        this.socketId = socketId;

        String webSocketId = String.valueOf(hashCode());
        webSocketClient = webSocketFactory.createWebSocketClient(webSocketId);
        webSocketClient.setConnectTimeout(5 * 1000L);
        webSocketClient.setStopTimeout(3000);
        webSocketClient.setMaxIdleTimeout(150000);
        webSocketClient.start();

        logger.debug("Connecting to Gardena Webservice ({})", socketId);
        session = (WebSocketSession) webSocketClient.connect(this, new URI(websocketUrl)).get();
        session.setStopTimeout(3000);
    }

    /**
     * Stops the websocket session.
     */
    public synchronized void stop() {
        closing = true;
        final ScheduledFuture<?> connectionTracker = this.connectionTracker;
        if (connectionTracker != null) {
            connectionTracker.cancel(true);
        }
        if (isRunning()) {
            logger.debug("Closing Gardena Webservice client ({})", socketId);
            try {
                session.close();
            } catch (Exception ex) {
                // ignore
            } finally {
                try {
                    webSocketClient.stop();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Returns true, if the websocket is running.
     */
    public synchronized boolean isRunning() {
        return session.isOpen();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        closing = false;
        logger.debug("Connected to Homematic IP WebSocket ({})", socketId);
        connectionTracker = scheduler.scheduleWithFixedDelay(this::sendKeepAlivePing, 30, 30, TimeUnit.SECONDS);
    }

    @OnWebSocketFrame
    public void onFrame(Frame pong) {
        if (pong instanceof PongFrame) {
            lastPong = Instant.now();
            logger.trace("Pong received ({})", socketId);
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        if (!closing) {
            logger.debug("Connection to Homematic IP WebSocket was closed ({}): code: {}, reason: {}", socketId, statusCode,
                    reason);
            socketEventListener.onWebSocketClose();
        }
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        if (!closing) {
            logger.warn("Homematic IP WebSocket error ({}): {}, restarting", socketId, cause.getMessage());
            logger.debug("{}", cause.getMessage(), cause);
            socketEventListener.onWebSocketError(cause);
            try {
                session.close();
                Thread.sleep(5000L);
                session.start();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        if (!closing) {
            logger.trace("<<< event ({}): {}", socketId, msg);
            socketEventListener.onWebSocketReceive(msg);
        }
    }

    /**
     * Sends a ping to tell the Gardena smart system that the client is alive.
     */
    private void sendKeepAlivePing() {
        try {
            logger.trace("Sending ping ({})", socketId);
            session.getRemote().sendPing(pingPayload);
        } catch (IOException ex) {
            logger.debug("{}", ex.getMessage());
        }
    }
}
