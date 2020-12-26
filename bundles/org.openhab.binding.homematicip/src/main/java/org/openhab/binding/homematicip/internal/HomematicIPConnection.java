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
package org.openhab.binding.homematicip.internal;

import org.openhab.binding.homematicip.internal.model.request.*;
import org.openhab.binding.homematicip.internal.model.response.AuthConfirmTokenResponse;
import org.openhab.binding.homematicip.internal.model.response.AuthRequestTokenResponse;
import org.openhab.binding.homematicip.internal.model.response.LookupResponse;
import org.openhab.binding.homematicip.internal.model.transport.Request;
import org.openhab.binding.homematicip.internal.model.transport.Transport;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Class representing an online connection to the Homematic IP Service
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public class HomematicIPConnection {

    private final String uuid;
    private final String accessPointId;
    private final Transport transport;
    private final ClientCharacteristics clientCharacteristics;
    private String urlREST;
    private String urlWebSocket;

    public HomematicIPConnection(String uuid, String accessPointId, Transport transport) throws NoSuchAlgorithmException, IOException {
        this.uuid = uuid;
        this.accessPointId = accessPointId.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        this.transport = transport;
        this.transport.setAccessPointId(this.accessPointId);
        this.clientCharacteristics = createClientCharacteristics();
        lookup();
    }

    private void lookup() throws IOException {
        var response = transport.post(
                new Request<>(
                        Transport.LOOKUP_URL,
                            new LookupRequest(accessPointId, clientCharacteristics)),
                LookupResponse.class);
        if (response.getStatusCode() != 200 || response.getResponseBody() == null) {
            throw new IOException("Unexpected response for lookup: " + response);
        }
        this.urlREST = response.getResponseBody().getUrlREST();
        this.urlWebSocket = response.getResponseBody().getUrlWebSocket();
    }

    private ClientCharacteristics createClientCharacteristics() {
        var characteristics = new ClientCharacteristics();
        characteristics.setApiVersion(Transport.API_VERSION);
        characteristics.setApplicationIdentifier("homematicip-openhab");
        characteristics.setDeviceType("Computer");
        characteristics.setApplicationVersion("1.0");
        characteristics.setOsType(System.getProperty("os.name"));
        characteristics.setOsVersion(System.getProperty("os.version"));
        characteristics.setLanguage(Locale.getDefault().getLanguage());
        characteristics.setDeviceManufacturer("none");
        return characteristics;
    }

    public void authConnectionRequest() throws IOException {
        final var response = transport.post(
                new Request<>(
                        restUrl("/hmip/auth/connectionRequest"),
                                new AuthConnectionRequest(uuid, "OpenHAB Homematic IP", accessPointId)),
                Void.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authConnectionRequest: " + response);
        }
    }

    public boolean authIsRequestAcknowledgedRequest() throws IOException {
        final var response = transport.post(
                new Request<>(
                        restUrl("/hmip/auth/isRequestAcknowledged"),
                            new AuthIsRequestAcknowledgedRequest(uuid)),
                Void.class);
        switch (response.getStatusCode()) {
            case 200:
                return true;
            case 400:
                return false;
            default:
                throw new IOException("Unexpected response for authIsRequestAcknowledgedRequest: " + response);
        }
    }

    public AuthRequestTokenResponse authRequestToken() throws IOException {
        final var response = transport.post(
                new Request<>(
                        restUrl("/hmip/auth/requestAuthToken"),
                            new AuthRequestTokenRequest(uuid)),
                AuthRequestTokenResponse.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authRequestToken: " + response);
        }
        return response.getResponseBody();
    }

    public AuthConfirmTokenResponse authConfirmToken(String authToken) throws IOException {
        final var response = transport.post(
                new Request<>(
                        restUrl("/hmip/auth/confirmAuthToken"),
                        new AuthConfirmTokenRequest(uuid, authToken)),
                AuthConfirmTokenResponse.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authConfirmToken: " + response);
        }
        return response.getResponseBody();
    }

    private String restUrl(String path) {
        return this.urlREST + path;
    }

}
