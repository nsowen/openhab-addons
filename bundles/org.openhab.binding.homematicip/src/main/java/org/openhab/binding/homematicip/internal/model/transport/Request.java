/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.homematicip.internal.model.transport;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Request class
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-24
 */
public class Request<T,V> {

    private T requestBody;
    private String url;
    private boolean authenticated = true;
    private Map<String,String> additionalHeaders = new HashMap<>();
    private long timeoutMillis = 5000L;

    public Request(String url, T requestBody, boolean authenticated) {
        this.url = url;
        this.requestBody = requestBody;
        this.authenticated = authenticated;
    }

    public Request(String url, T requestBody) {
        this(url, requestBody, true);
    }

    public Request(String url) {
        this.url = url;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Map<String, String> getAdditionalHeaders() {
        return additionalHeaders;
    }

    public void setAdditionalHeaders(Map<String, String> additionalHeaders) {
        this.additionalHeaders = additionalHeaders;
    }

    public void addAdditionalHeader(String key, String value) {
        this.additionalHeaders.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request<T,V> request = (Request<T,V>) o;
        return authenticated == request.authenticated && Objects.equals(requestBody, request.requestBody) && Objects.equals(url, request.url) && Objects.equals(additionalHeaders, request.additionalHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestBody, url, authenticated, additionalHeaders);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Request.class.getSimpleName() + "[", "]")
                .add("requestBody=" + requestBody)
                .add("url='" + url + "'")
                .add("authenticated=" + authenticated)
                .add("additionalHeaders=" + additionalHeaders)
                .toString();
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }
}
