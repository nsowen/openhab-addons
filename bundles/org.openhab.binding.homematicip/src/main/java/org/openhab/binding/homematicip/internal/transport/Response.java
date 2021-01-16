/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
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
package org.openhab.binding.homematicip.internal.transport;

import java.util.Objects;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;

/**
 * Response class
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-24
 */
public class Response<T extends HomematicIPObject, V extends HomematicIPObject> {

    private V responseBody;
    private int statusCode;
    private String statusText;
    private Request<T, V> request;
    private long timeMillis;

    public V getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(V responseBody) {
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Request<T, V> getRequest() {
        return request;
    }

    public void setRequest(Request<T, V> request) {
        this.request = request;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Response<?, ?> response = (Response<?, ?>) o;
        return statusCode == response.statusCode && Objects.equals(responseBody, response.responseBody)
                && Objects.equals(statusText, response.statusText) && Objects.equals(request, response.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseBody, statusCode, statusText, request);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Response.class.getSimpleName() + "[", "]").add("responseBody=" + responseBody)
                .add("statusCode=" + statusCode).add("statusText='" + statusText + "'").add("request=" + request)
                .add("timeMillis=" + timeMillis).toString();
    }
}