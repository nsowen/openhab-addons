/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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
package org.openhab.binding.homematicip.internal.model.transport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.openhab.core.io.net.http.WebSocketFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation using Java's URLConnection
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-24
 */
public class HttpTransport implements Transport {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");

    private final Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
    private final Map<String, String> defaultHeaders = new HashMap<>();
    private final WebSocketFactory webSocketFactory;
    private final OkHttpClient client;

    private WebSocketClient wssClient;

    public HttpTransport(WebSocketFactory webSocketFactory) {
        this.webSocketFactory = webSocketFactory;
        this.client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .callTimeout(10, TimeUnit.SECONDS)
                .build();
        setDefaultHeader("Content-Type", "application/json");
        setDefaultHeader("Accept", "application/json");
        setDefaultHeader(HEADER_VERSION, API_VERSION);
    }

    private void setDefaultHeader(String key, String value) {
        this.defaultHeaders.put(key, value);
    }

    @Override
    public void setClientAuth(String clientAuth) {
        setDefaultHeader(HEADER_CLIENTAUTH, clientAuth);
    }

    @Override
    public void setAuthToken(String authToken) {
        setDefaultHeader(HEADER_AUTHTOKEN, authToken);
    }

    @Override
    public <T, V> CompletableFuture<Response<T, V>> postAsync(Request<T, V> request, Class<V> clazz, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return post(request, clazz);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    @Override
    public <T, V> Response<T, V> post(Request<T, V> request, Class<V> clazz) throws IOException {
        final var start = System.currentTimeMillis();
        final var response = new Response();
        response.setStatusCode(501);
        response.setStatusText("Unknown error");
        response.setRequest(request);
        try {
            final var body = gson.toJson(request.getRequestBody());
            final var requestBuilder = new okhttp3.Request.Builder().url(request.getUrl());
            if (body != null && body.length() > 0) {
                requestBuilder.post(RequestBody.create(gson.toJson(request.getRequestBody()), MEDIA_TYPE_JSON));
            }
            defaultHeaders.forEach((key, value) -> requestBuilder.addHeader(key, value));
            request.getAdditionalHeaders().forEach((key, value) -> requestBuilder.addHeader(key, value));
            try (var httpResponse = client.newCall(requestBuilder.build()).execute()) {
                response.setStatusCode(httpResponse.code());
                response.setStatusText(httpResponse.message());
                final var responseBody = httpResponse.body();
                if (responseBody.contentLength() > 0) {
                    response.setResponseBody(gson.fromJson(new StringReader(responseBody.string()), clazz));
                }
            }
        } catch (IOException | JsonParseException e) {
            response.setStatusText("Exception: " + e.getMessage());
            throw e;
        } finally {
            response.setTimeMillis(System.currentTimeMillis() - start);
        }
        return response;
    }

    @Override
    public <U> void enableWebSocket(String wssUrl, WebSocketListener<U> listener) {
    }

    @Override
    public void disableWebSocket() {
    }


}
