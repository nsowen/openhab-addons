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

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.homematicip.internal.model.request.*;
import org.openhab.binding.homematicip.internal.model.response.AuthConfirmTokenResponse;
import org.openhab.binding.homematicip.internal.model.response.AuthRequestTokenResponse;
import org.openhab.binding.homematicip.internal.model.response.GetCurrentStateResponse;
import org.openhab.binding.homematicip.internal.model.response.LookupResponse;
import org.openhab.binding.homematicip.internal.model.transport.Request;
import org.openhab.binding.homematicip.internal.model.transport.Transport;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class representing an online connection to the Homematic IP Service
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
@NonNullByDefault
public class HomematicIPConnection {

    private final String uuid;
    private final String accessPointId;
    private final Transport transport;
    private final ClientCharacteristics clientCharacteristics;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private @Nullable String urlREST;
    private @Nullable String urlWebSocket;

    public HomematicIPConnection(String uuid, String accessPointId, Transport transport) throws NoSuchAlgorithmException, IOException {
        this(uuid, accessPointId, null, transport);
    }
    public HomematicIPConnection(String uuid, String accessPointId, String authToken, Transport transport) throws NoSuchAlgorithmException {
        this.uuid = uuid;
        this.accessPointId = accessPointId.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        this.transport = transport;
        this.transport.setAccessPointId(this.accessPointId);
        this.transport.setAuthToken(authToken);
        this.clientCharacteristics = createClientCharacteristics();
    }

    public boolean isReadyForPairing() {
        return (!transport.hasAuthToken() && StringUtils.isNotEmpty(urlREST));
    }

    public boolean isReadyForUse() {
        return (transport.hasAuthToken() && StringUtils.isNotEmpty(urlREST));
    }

    public void setAuthToken(String authToken) {
        this.transport.setAuthToken(authToken);
    }

    public CompletableFuture<Void> initAsync(Executor executor) {
        var wl = lock.writeLock();
        return CompletableFuture.supplyAsync(() -> {
            try {
              lookup();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
            return null;
        }, executor);
    }

    private void lookup() throws IOException {
        var wl = lock.writeLock();
        var response = transport.post(
                new Request<>(
                        Transport.LOOKUP_URL,
                            new LookupRequest(accessPointId, clientCharacteristics), false),
                LookupResponse.class);
        if (response.getStatusCode() != 200 || response.getResponseBody() == null) {
            throw new IOException("Unexpected response for lookup: " + response);
        }
        try {
            wl.lock();
            this.urlREST = response.getResponseBody().getUrlREST();
            this.urlWebSocket = response.getResponseBody().getUrlWebSocket();
        } finally {
            if (wl.isHeldByCurrentThread()) {
                wl.unlock();
            }
        }
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

    public CompletableFuture<HomematicIPConnection> getCurrentState(Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                var request = new Request<GetCurrentStateRequest, GetCurrentStateResponse>(
                        restUrl("/hmip/home/getCurrentState"),
                        new GetCurrentStateRequest(accessPointId, clientCharacteristics));
                final var response = transport.post(request, GetCurrentStateResponse.class);
                if (response.getStatusCode() != 200) {
                    throw new IllegalStateException("Expected 200 ok");
                }
            } catch (Exception e) {
                throw new CompletionException(e);
            }
            return this;
        }, executor);
    }

    public void authConnectionRequest() throws IOException {
        final var request = new Request<AuthConnectionRequest, Void>(restUrl("/hmip/auth/connectionRequest"),
                new AuthConnectionRequest(uuid, "OpenHAB Homematic IP", accessPointId), false);
        final var response = transport.post(request, Void.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authConnectionRequest: " + response);
        }
    }

    public boolean authIsRequestAcknowledgedRequest() throws IOException {
        final var request = new Request<AuthIsRequestAcknowledgedRequest, Void>(restUrl("/hmip/auth/isRequestAcknowledged"),
                new AuthIsRequestAcknowledgedRequest(uuid), false);
        final var response = transport.post(request, Void.class);
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
        final var request = new Request<AuthRequestTokenRequest, AuthRequestTokenResponse>(restUrl("/hmip/auth/requestAuthToken"),
                new AuthRequestTokenRequest(uuid), false);
        final var response = transport.post(request, AuthRequestTokenResponse.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authRequestToken: " + response);
        }
        return response.getResponseBody();
    }

    public AuthConfirmTokenResponse authConfirmToken(String authToken) throws IOException {
        final var request = new Request<AuthConfirmTokenRequest, AuthConfirmTokenResponse>(restUrl("/hmip/auth/confirmAuthToken"),
                new AuthConfirmTokenRequest(uuid, authToken), false);
        final var response = transport.post(request, AuthConfirmTokenResponse.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authConfirmToken: " + response);
        }
        return response.getResponseBody();
    }

    private String restUrl(String path) {
        var rl = lock.readLock();
        try {
            rl.lock();
            if (this.urlREST == null) {
                throw new IllegalStateException("Not initialized.");
            }
            return this.urlREST + path;
        } finally {
            rl.unlock();
        }
    }

}
