package org.openhab.binding.homematicip.internal.transport;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.extensions.Frame;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.eclipse.jetty.websocket.common.frames.PongFrame;
import org.openhab.core.io.net.http.WebSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link HomematicIPWebSocket} implements the websocket for receiving constant updates from the Homematic IP
 * cloud.
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-31
 */
@NonNullByDefault
@WebSocket
public class HomematicIPWebSocket {

    private final Logger logger = LoggerFactory.getLogger(HomematicIPWebSocket.class);
    private final WebSocketListener socketEventListener;
    private final long WEBSOCKET_IDLE_TIMEOUT = 300;

    private @Nullable WebSocketSession session;
    private WebSocketClient webSocketClient;
    private boolean closing;
    private Instant lastPong = Instant.now();
    private ScheduledExecutorService scheduler;
    private @Nullable ScheduledFuture<?> connectionTracker;
    private ByteBuffer pingPayload = ByteBuffer.wrap("ping".getBytes(StandardCharsets.UTF_8));
    private ReentrantLock lock = new ReentrantLock();
    private @Nullable volatile String currentUrl;

    public HomematicIPWebSocket(WebSocketListener socketEventListener, ScheduledExecutorService scheduler,
            WebSocketFactory webSocketFactory) {
        this.socketEventListener = socketEventListener;
        this.scheduler = scheduler;

        var webSocketId = String.valueOf(hashCode());
        webSocketClient = webSocketFactory.createWebSocketClient(webSocketId);
        webSocketClient.setConnectTimeout(5 * 1000L);
        webSocketClient.setStopTimeout(3000);
        webSocketClient.setMaxIdleTimeout(150000);
    }

    public void start(String wssUrl, Map<String, String> headers, boolean forceReconnect) throws Exception {
        lock.lock();
        currentUrl = wssUrl;
        webSocketClient.start();
        try {
            if (session != null && session.isOpen() && forceReconnect) {
                stop();
            }
            var request = new ClientUpgradeRequest();
            request.setSubProtocols();
            headers.forEach(request::setHeader);
            logger.debug("Connecting to Homematic IP WebSocket ({})", wssUrl);
            session = (WebSocketSession) webSocketClient.connect(this, new URI(currentUrl), request).get();
            session.setStopTimeout(3000);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Stops the websocket session.
     */
    public void stop() {
        lock.lock();
        try {
            closing = true;
            final ScheduledFuture<?> connectionTracker = this.connectionTracker;
            if (connectionTracker != null) {
                connectionTracker.cancel(true);
            }
            if (isRunning()) {
                logger.debug("Closing Homematic IP WebSocket client ({})", currentUrl);
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
        } finally {
            lock.unlock();
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
        logger.debug("Connected to Homematic IP WebSocket ({}): {}", currentUrl,
                session.getUpgradeResponse().getStatusCode() + " " + session.getUpgradeResponse().getStatusReason()
                        + "");
        connectionTracker = scheduler.scheduleWithFixedDelay(this::sendKeepAlivePing, 30, 30, TimeUnit.SECONDS);
    }

    @OnWebSocketFrame
    public void onFrame(Frame frame) {
        if (frame instanceof PongFrame) {
            lastPong = Instant.now();
            logger.trace("Pong received ({})", currentUrl);
        } else {
            logger.trace("Frame received: {} ({})", frame, currentUrl);
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        if (!closing) {
            logger.debug("Connection to Homematic IP WebSocket was closed ({}): code: {}, reason: {}", currentUrl,
                    statusCode, reason);
            socketEventListener.onWebSocketClose();
        }
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        if (!closing) {
            logger.warn("Homematic IP WebSocket error ({}): {}, restarting", currentUrl, cause.getMessage());
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
    public void onMessage(byte[] buffer, int offset, int length) {
        try {
            // we expect utf-8 strings here
            onMessage(new String(buffer, offset, length, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("Cannot decode payload: {}", e.getMessage());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        if (!closing) {
            logger.trace("<<< event ({}): {}", currentUrl, msg);
            socketEventListener.onWebSocketReceive(msg);
        }
    }

    /**
     * Sends a ping to tell the Gardena smart system that the client is alive.
     */
    private void sendKeepAlivePing() {
        try {
            logger.trace("Sending ping ({})", currentUrl);
            session.getRemote().sendPing(pingPayload);
        } catch (IOException ex) {
            logger.debug("{}", ex.getMessage());
        }
    }
}
