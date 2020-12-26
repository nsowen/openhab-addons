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
package org.openhab.binding.homematicip.internal.model.response;

/**
 * Response for Lookup request
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-24
 */
public class LookupResponse {

    private String urlREST;
    private String urlWebSocket;
    private String apiVersion;
    private String primaryAccessPointId;
    private String requestingAccessPointId;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getPrimaryAccessPointId() {
        return primaryAccessPointId;
    }

    public void setPrimaryAccessPointId(String primaryAccessPointId) {
        this.primaryAccessPointId = primaryAccessPointId;
    }

    public String getRequestingAccessPointId() {
        return requestingAccessPointId;
    }

    public void setRequestingAccessPointId(String requestingAccessPointId) {
        this.requestingAccessPointId = requestingAccessPointId;
    }

    public String getUrlREST() {
        return urlREST;
    }

    public void setUrlREST(String urlREST) {
        this.urlREST = urlREST;
    }

    public String getUrlWebSocket() {
        return urlWebSocket;
    }

    public void setUrlWebSocket(String urlWebSocket) {
        this.urlWebSocket = urlWebSocket;
    }
}
