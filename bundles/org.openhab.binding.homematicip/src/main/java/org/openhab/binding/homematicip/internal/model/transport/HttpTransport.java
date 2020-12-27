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
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.*;
import org.eclipse.jetty.util.Fields;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.io.net.http.WebSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpCookie;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Default implementation using Java's URLConnection
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-24
 */
public class HttpTransport implements Transport {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private final Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
    private final Map<String, String> defaultHeaders = new HashMap<>();
    private final WebSocketFactory webSocketFactory;
    private final HttpClient client;

    private WebSocketClient wssClient;

    public HttpTransport(HttpClientFactory httpClientFactory, WebSocketFactory webSocketFactory) {
        this.webSocketFactory = webSocketFactory;
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
            if (request.isAuthenticated() && !defaultHeaders.containsKey(HEADER_AUTHTOKEN)) {
                throw new IllegalStateException("Cannot perform authenticated requests without AUTHTOKEN");
            }
            final var body = gson.toJson(request.getRequestBody());
            var httpRequest = client.newRequest(request.getUrl())
                    .method(HttpMethod.POST);
            if (body != null && body.length() > 0) {
                httpRequest.content(new StringContentProvider(gson.toJson(request.getRequestBody())));
            }
            defaultHeaders.forEach((key, value) -> httpRequest.header(key, value));
            request.getAdditionalHeaders().forEach((key, value) -> httpRequest.header(key, value));
            var httpResponse = new LoggingHttpRequest(httpRequest).send();
            response.setStatusCode(httpResponse.getStatus());
            response.setStatusText(httpResponse.getReason());
            final var responseBody = httpResponse.getContentAsString();
            if (responseBody != null && responseBody.length() > 0) {
                response.setResponseBody(gson.fromJson(new StringReader(responseBody), clazz));
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
    public <U> void enableWebSocket(String wssUrl, WebSocketListener<U> listener) {
    }

    @Override
    public void disableWebSocket() {
    }

    public static class LoggingHttpRequest implements org.eclipse.jetty.client.api.Request {

        private final AtomicLong nextId = new AtomicLong();
        private final Logger log = LoggerFactory.getLogger(LoggingHttpRequest.class);
        private final org.eclipse.jetty.client.api.Request delegate;

        public LoggingHttpRequest(org.eclipse.jetty.client.api.Request request) {
            this.delegate = Objects.requireNonNull(request);
            addListeners(this.delegate);
        }

        private void addListeners(org.eclipse.jetty.client.api.Request request)
        {
            long id = nextId.getAndIncrement();
            StringBuilder group = new StringBuilder();
            request.onRequestBegin(theRequest -> group.append("Request " + id + "\n" +
                    id + " > " + theRequest.getMethod() + " " + theRequest.getURI() + "\n"));
            request.onRequestHeaders(theRequest ->
            {
                for (HttpField header : theRequest.getHeaders())
                    group.append(id + " > " + header + "\n");
            });

            StringBuilder contentBuffer = new StringBuilder();
            request.onRequestContent((theRequest, content) ->
                    contentBuffer.append(new String(content.array(), getCharset(theRequest.getHeaders()))));
            request.onRequestSuccess(theRequest ->
            {
                if (contentBuffer.length() > 0)
                {
                    group.append("\n" +
                            contentBuffer.toString());
                }
                log.debug(group.toString());
                contentBuffer.delete(0, contentBuffer.length());
                group.delete(0, group.length());
            });

            request.onResponseBegin(theResponse ->
            {
                group.append("Response " + id + "\n" +
                        id + " < " + theResponse.getVersion() + " " + theResponse.getStatus());
                if (theResponse.getReason() != null)
                    group.append(" " + theResponse.getReason());
                group.append("\n");
            });
            request.onResponseHeaders(theResponse ->
            {
                for (HttpField header : theResponse.getHeaders())
                    group.append(id + " < " + header + "\n");
            });
            request.onResponseContent((theResponse, content) -> {
                byte[] bytes;
                if (content.hasArray()) {
                    bytes = content.array();
                } else {
                    bytes = new byte[content.remaining()];
                    content.get(bytes);
                }
                if (bytes.length > 0) {
                    contentBuffer.append(new String(bytes, getCharset(theResponse.getHeaders())));
                }
            });
            request.onResponseSuccess(theResponse ->
            {
                if (contentBuffer.length() > 0)
                {
                    group.append("\n" +
                            contentBuffer.toString());
                }
                log.debug(group.toString());
            });
        }

        /**
         * @param headers HTTP headers
         * @return the charset associated with the request or response body
         */
        private Charset getCharset(HttpFields headers)
        {
            String contentType = headers.get(HttpHeader.CONTENT_TYPE);
            if (contentType == null) {
                return StandardCharsets.UTF_8;
            }
            String[] tokens = contentType.toLowerCase(Locale.US).split("charset=");
            if (tokens.length != 2) {
                return StandardCharsets.UTF_8;
            }
            String encoding = tokens[1].replaceAll("[;\"]", "");
            return Charset.forName(encoding);
        }

        @Override
        public String getScheme() {
            return delegate.getScheme();
        }

        @Override
        public org.eclipse.jetty.client.api.Request scheme(String s) {
            return delegate.scheme(s);
        }

        @Override
        public String getHost() {
            return delegate.getHost();
        }

        @Override
        public int getPort() {
            return delegate.getPort();
        }

        @Override
        public String getMethod() {
            return delegate.getMethod();
        }

        @Override
        public org.eclipse.jetty.client.api.Request method(HttpMethod httpMethod) {
            return delegate.method(httpMethod);
        }

        @Override
        public org.eclipse.jetty.client.api.Request method(String s) {
            return delegate.method(s);
        }

        @Override
        public String getPath() {
            return delegate.getPath();
        }

        @Override
        public org.eclipse.jetty.client.api.Request path(String s) {
            return delegate.path(s);
        }

        @Override
        public String getQuery() {
            return delegate.getQuery();
        }

        @Override
        public URI getURI() {
            return delegate.getURI();
        }

        @Override
        public HttpVersion getVersion() {
            return delegate.getVersion();
        }

        @Override
        public org.eclipse.jetty.client.api.Request version(HttpVersion httpVersion) {
            return delegate.version(httpVersion);
        }

        @Override
        public Fields getParams() {
            return delegate.getParams();
        }

        @Override
        public org.eclipse.jetty.client.api.Request param(String s, String s1) {
            return delegate.param(s, s1);
        }

        @Override
        public HttpFields getHeaders() {
            return delegate.getHeaders();
        }

        @Override
        public org.eclipse.jetty.client.api.Request header(String s, String s1) {
            return delegate.header(s, s1);
        }

        @Override
        public org.eclipse.jetty.client.api.Request header(HttpHeader httpHeader, String s) {
            return delegate.header(httpHeader, s);
        }

        @Override
        public List<HttpCookie> getCookies() {
            return delegate.getCookies();
        }

        @Override
        public org.eclipse.jetty.client.api.Request cookie(HttpCookie httpCookie) {
            return delegate.cookie(httpCookie);
        }

        @Override
        public org.eclipse.jetty.client.api.Request attribute(String s, Object o) {
            return delegate.attribute(s, o);
        }

        @Override
        public Map<String, Object> getAttributes() {
            return delegate.getAttributes();
        }

        @Override
        public ContentProvider getContent() {
            return delegate.getContent();
        }

        @Override
        public org.eclipse.jetty.client.api.Request content(ContentProvider contentProvider) {
            return delegate.content(contentProvider);
        }

        @Override
        public org.eclipse.jetty.client.api.Request content(ContentProvider contentProvider, String s) {
            return delegate.content(contentProvider, s);
        }

        @Override
        public org.eclipse.jetty.client.api.Request file(Path path) throws IOException {
            return delegate.file(path);
        }

        @Override
        public org.eclipse.jetty.client.api.Request file(Path path, String s) throws IOException {
            return delegate.file(path, s);
        }

        @Override
        public String getAgent() {
            return delegate.getAgent();
        }

        @Override
        public org.eclipse.jetty.client.api.Request agent(String s) {
            return delegate.agent(s);
        }

        @Override
        public org.eclipse.jetty.client.api.Request accept(String... strings) {
            return delegate.accept(strings);
        }

        @Override
        public long getIdleTimeout() {
            return delegate.getIdleTimeout();
        }

        @Override
        public org.eclipse.jetty.client.api.Request idleTimeout(long l, TimeUnit timeUnit) {
            return delegate.idleTimeout(l, timeUnit);
        }

        @Override
        public long getTimeout() {
            return delegate.getTimeout();
        }

        @Override
        public org.eclipse.jetty.client.api.Request timeout(long l, TimeUnit timeUnit) {
            return delegate.timeout(l, timeUnit);
        }

        @Override
        public boolean isFollowRedirects() {
            return delegate.isFollowRedirects();
        }

        @Override
        public org.eclipse.jetty.client.api.Request followRedirects(boolean b) {
            return delegate.followRedirects(b);
        }

        @Override
        public <T extends RequestListener> List<T> getRequestListeners(Class<T> aClass) {
            return delegate.getRequestListeners(aClass);
        }

        @Override
        public org.eclipse.jetty.client.api.Request listener(Listener listener) {
            return delegate.listener(listener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onRequestQueued(QueuedListener queuedListener) {
            return delegate.onRequestQueued(queuedListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onRequestBegin(BeginListener beginListener) {
            return delegate.onRequestBegin(beginListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onRequestHeaders(HeadersListener headersListener) {
            return delegate.onRequestHeaders(headersListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onRequestCommit(CommitListener commitListener) {
            return delegate.onRequestCommit(commitListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onRequestContent(ContentListener contentListener) {
            return delegate.onRequestContent(contentListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onRequestSuccess(SuccessListener successListener) {
            return delegate.onRequestSuccess(successListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onRequestFailure(FailureListener failureListener) {
            return delegate.onRequestFailure(failureListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onResponseBegin(org.eclipse.jetty.client.api.Response.BeginListener beginListener) {
            return delegate.onResponseBegin(beginListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onResponseHeader(org.eclipse.jetty.client.api.Response.HeaderListener headerListener) {
            return delegate.onResponseHeader(headerListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onResponseHeaders(org.eclipse.jetty.client.api.Response.HeadersListener headersListener) {
            return delegate.onResponseHeaders(headersListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onResponseContent(org.eclipse.jetty.client.api.Response.ContentListener contentListener) {
            return delegate.onResponseContent(contentListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onResponseContentAsync(org.eclipse.jetty.client.api.Response.AsyncContentListener asyncContentListener) {
            return delegate.onResponseContentAsync(asyncContentListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onResponseSuccess(org.eclipse.jetty.client.api.Response.SuccessListener successListener) {
            return delegate.onResponseSuccess(successListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onResponseFailure(org.eclipse.jetty.client.api.Response.FailureListener failureListener) {
            return delegate.onResponseFailure(failureListener);
        }

        @Override
        public org.eclipse.jetty.client.api.Request onComplete(org.eclipse.jetty.client.api.Response.CompleteListener completeListener) {
            return delegate.onComplete(completeListener);
        }

        @Override
        public ContentResponse send() throws InterruptedException, TimeoutException, ExecutionException {
            return delegate.send();
        }

        @Override
        public void send(org.eclipse.jetty.client.api.Response.CompleteListener completeListener) {
            delegate.send(completeListener);
        }

        @Override
        public boolean abort(Throwable throwable) {
            return delegate.abort(throwable);
        }

        @Override
        public Throwable getAbortCause() {
            return delegate.getAbortCause();
        }
    }
}
