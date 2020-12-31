/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 * <p>
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 * <p>
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * <p>
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.homematicip.internal.transport;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.*;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.*;
import org.openhab.binding.homematicip.internal.HomematicIPEventListener;
import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.event.StateChange;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.io.net.http.WebSocketFactory;

import com.google.gson.JsonParseException;

/**
 * Transport for Homematic IP requests using OpenHAB's {@link HttpClientFactory HttpClientFactory}
 * implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-24
 */
public class HttpTransport implements Transport, WebSocketListener {

    private final Map<String, String> defaultHeaders = new HashMap<>();
    private final WebSocketFactory webSocketFactory;
    private final HttpClient client;
    private final ScheduledExecutorService scheduler;

    private HomematicIPEventListener websocketListener;
    private HomematicIPWebSocket websocket;

    /**
     * Creates a new http transport based on OpenHABs primary {@link HttpClientFactory HttpClientFactory}
     *
     * @param httpClientFactory client factory given by OpenHAB
     * @param webSocketFactory web socket factory given by OpenHAB
     */
    public HttpTransport(HttpClientFactory httpClientFactory, WebSocketFactory webSocketFactory,
            ScheduledExecutorService scheduler) {
        this.webSocketFactory = webSocketFactory;
        this.websocket = new HomematicIPWebSocket(this, scheduler, webSocketFactory);
        this.scheduler = scheduler;
        this.client = httpClientFactory.createHttpClient("homeamticip");
        client.setConnectTimeout(5 * 1000L);
        client.setIdleTimeout(5 * 1000L);
        try {
            client.start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        setDefaultHeader("Content-Type", "application/json");
        setDefaultHeader("Accept", "application/json");
        setDefaultHeader(HEADER_VERSION, API_VERSION);
    }

    /**
     * Adds data to the default header map used in each request
     *
     * @param key name of header
     * @param value value of header
     */
    private void setDefaultHeader(String key, String value) {
        this.defaultHeaders.put(key, value);
    }

    /**
     * Removes header from default header map (used in each request)
     *
     * @param header header name
     */
    private void removeDefaultHeader(String header) {
        defaultHeaders.remove(header);
    }

    private String getDefaultHeader(String header) {
        return defaultHeaders.get(header);
    }

    @Override
    public void setClientAuth(String clientAuth) {
        setDefaultHeader(HEADER_CLIENTAUTH, clientAuth);
    }

    @Override
    public void setAuthToken(@Nullable String authToken) {
        if (authToken == null) {
            removeDefaultHeader(HEADER_AUTHTOKEN);
        } else {
            setDefaultHeader(HEADER_AUTHTOKEN, authToken);
        }
    }

    public String getClientAuth() {
        return getDefaultHeader(HEADER_CLIENTAUTH);
    }

    public String getAuthToken() {
        return getDefaultHeader(HEADER_AUTHTOKEN);
    }

    @Override
    public boolean hasAuthToken() {
        return StringUtils.isNotEmpty(defaultHeaders.get(HEADER_AUTHTOKEN));
    }

    @Override
    public <T extends HomematicIPObject, V extends HomematicIPObject> CompletableFuture<Response<T, V>> postAsync(
            Request<T, V> request, Class<V> clazz, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return post(request, clazz);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    @Override
    public <T extends HomematicIPObject, V extends HomematicIPObject> Response<T, V> post(Request<T, V> request,
            Class<V> clazz) throws IOException {
        final var start = System.currentTimeMillis();
        final var response = new Response();
        response.setStatusCode(501);
        response.setStatusText("Unknown error");
        response.setRequest(request);
        try {
            if (request.isAuthenticated() && !defaultHeaders.containsKey(HEADER_AUTHTOKEN)) {
                throw new IllegalStateException("Cannot perform authenticated requests without AUTHTOKEN");
            }
            final var body = HomematicIPObject.toJson(request.getRequestBody());
            var httpRequest = client.newRequest(request.getUrl()).method(HttpMethod.POST);
            if (body != null && body.length() > 0) {
                httpRequest.content(new StringContentProvider(body));
            }
            defaultHeaders.forEach((key, value) -> httpRequest.header(key, value));
            request.getAdditionalHeaders().forEach((key, value) -> httpRequest.header(key, value));
            var httpResponse = new LoggingHttpRequest(httpRequest).send();
            response.setStatusCode(httpResponse.getStatus());
            response.setStatusText(httpResponse.getReason());
            final var responseBody = httpResponse.getContentAsString();
            if (responseBody != null && responseBody.length() > 0) {
                response.setResponseBody(HomematicIPObject.fromJson(new StringReader(responseBody), clazz));
            }
        } catch (JsonParseException e) {
            response.setStatusText("Exception: " + e.getMessage());
            throw e;
        } catch (InterruptedException | TimeoutException | ExecutionException e2) {
            response.setStatusText("Exception: " + e2.getMessage());
            throw new IllegalStateException(e2);
        } finally {
            response.setTimeMillis(System.currentTimeMillis() - start);
        }
        return response;
    }

    @Override
    public void enableWebSocket(String wssUrl, HomematicIPEventListener listener) throws IOException {
        if (!hasAuthToken()) {
            throw new IllegalStateException("Cannot enable websocket without AUTHTOKEN");
        }
        try {
            synchronized (this) {
                websocket.start(wssUrl, defaultHeaders, false);
                websocketListener = listener;
            }
        } catch (Exception e) {
            throw new IOException("Cannot create websocket: " + e.getMessage(), e);
        }
    }

    @Override
    public void disableWebSocket() {
        synchronized (this) {
            websocket.stop();
            websocket = null;
            websocketListener = null;
        }
    }

    @Override
    public void onWebSocketReceive(String data) {
        if (data != null && data.length() > 0) {
            var stateChange = (StateChange) HomematicIPObject.fromJson(data, StateChange.class);
            if (websocketListener != null && stateChange != null) {
                websocketListener.onReceive(stateChange);
            }
        }
    }

    @Override
    public void onWebSocketClose() {
    }

    @Override
    public void onWebSocketError(Throwable cause) {
    }
}
