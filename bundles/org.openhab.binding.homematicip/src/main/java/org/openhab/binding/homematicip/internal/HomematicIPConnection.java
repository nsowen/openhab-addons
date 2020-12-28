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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    /**
     * Creates a new Homematic IP cloud connection using the given uuid (client id) and
     * access point id (SGTIN) over the given transport implementation.
     *
     * @param uuid unique id of client application
     * @param accessPointId SGTIN / unique id of access point we are addressing over cloud
     * @param transport transport implementation to use
     * @throws NoSuchAlgorithmException in case SHA512 is not supported
     * @throws IOException in case of any I/O issue
     */
    public HomematicIPConnection(String uuid, @Nullable String accessPointId, Transport transport)
            throws NoSuchAlgorithmException, IOException {
        this(uuid, accessPointId, null, transport);
    }

    /**
     * Creates a new Homematic IP cloud connection using the given uuid (client id) and
     * access point id (SGTIN) over the given transport implementation.
     *
     * @param uuid unique id of client application
     * @param accessPointId SGTIN / unique id of access point we are addressing over cloud
     * @param authToken Secret auth token to be used for authenticated requests
     * @param transport transport implementation to use
     * @throws NoSuchAlgorithmException in case SHA512 is not supported
     * @throws IOException in case of any I/O issue
     */
    public HomematicIPConnection(String uuid, @Nullable String accessPointId, @Nullable String authToken,
            Transport transport) throws NoSuchAlgorithmException {
        this.uuid = uuid;
        this.accessPointId = accessPointId.replaceAll("[^A-Za-z0-9 ]", "").toUpperCase();
        this.transport = transport;
        this.transport.setAccessPointId(this.accessPointId);
        this.transport.setAuthToken(authToken);
        this.clientCharacteristics = createClientCharacteristics();
    }

    /**
     * Check if this connection can be used for a pairing request (lookup was successful and no secret
     * auth token has been set yet)
     * @return true if device is ready for pairing
     */
    public boolean isReadyForPairing() {
        return (!transport.hasAuthToken() && StringUtils.isNotEmpty(urlREST));
    }

    /**
     * Check if this connection has both a successful lookup and a valid secret auth token set. The
     * validity of the auth token has not been checked here.
     * @return true if device is ready for use
     */
    public boolean isReadyForUse() {
        return (transport.hasAuthToken() && StringUtils.isNotEmpty(urlREST));
    }

    /**
     * Sets the secret auth token for this connection to be used for authenticated requests
     * @param authToken secret auth token to use for this connection
     */
    public void setAuthToken(String authToken) {
        this.transport.setAuthToken(authToken);
    }

    /**
     * Initializes the connection by looking up the given Access Point ID using the cloud service
     * lookup URL.
     * @param executor executor pool to use for this lookup request
     * @return future containing the lookup result
     */
    public CompletableFuture<HomematicIPConnection> initAsync(Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                lookup();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
            return this;
        }, executor);
    }

    /**
     * Performs current state lookup, which will in fact return most readable data from the cloud configuration.
     * @param executor executor pool to use for this request
     * @return the connection
     */
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

    /**
     * Performs lookup request using the API by integrating the configured access point id
     * with some internal JSON structure that identifies this client.
     * @throws IOException in case any I/O error occurs
     */
    private void lookup() throws IOException {
        var wl = lock.writeLock();
        var response = transport.post(
                new Request<>(Transport.LOOKUP_URL, new LookupRequest(accessPointId, clientCharacteristics), false),
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

    /**
     * Create client characteristics used primarly in {@link #lookup() lookup()} process.
     * @return object containing client characteristics
     */
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

    /**
     * Pairing step 1: request a new authenticated connection for the given uuid (client application id) and
     * access point id.
     * @throws IOException in case of I/O error
     */
    public void authConnectionRequest() throws IOException {
        final var request = new Request<AuthConnectionRequest, Void>(restUrl("/hmip/auth/connectionRequest"),
                new AuthConnectionRequest(uuid, "OpenHAB Homematic IP", accessPointId), false);
        final var response = transport.post(request, Void.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authConnectionRequest: " + response);
        }
    }

    /**
     * Pairing step 2: checks if given uuid is already authenticated after the link button has been pressed.
     * Will either return 200 OK (if pressed/authenticated) or 400 Bad Request (in case link button has not yet
     * been pressed).
     * @throws IOException in case of I/O error
     */
    public boolean authIsRequestAcknowledgedRequest() throws IOException {
        final var request = new Request<AuthIsRequestAcknowledgedRequest, Void>(
                restUrl("/hmip/auth/isRequestAcknowledged"), new AuthIsRequestAcknowledgedRequest(uuid), false);
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

    /**
     * Pairing step 3: request a new secret authenticated token from the server for given uuid
     * @throws IOException in case of I/O error
     */
    public AuthRequestTokenResponse authRequestToken() throws IOException {
        final var request = new Request<AuthRequestTokenRequest, AuthRequestTokenResponse>(
                restUrl("/hmip/auth/requestAuthToken"), new AuthRequestTokenRequest(uuid), false);
        final var response = transport.post(request, AuthRequestTokenResponse.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authRequestToken: " + response);
        }
        return response.getResponseBody();
    }

    /**
     * Pairing step 4: confirm the receipt of the authenticated token.
     * @throws IOException in case of I/O error
     */
    public AuthConfirmTokenResponse authConfirmToken(String authToken) throws IOException {
        final var request = new Request<AuthConfirmTokenRequest, AuthConfirmTokenResponse>(
                restUrl("/hmip/auth/confirmAuthToken"), new AuthConfirmTokenRequest(uuid, authToken), false);
        final var response = transport.post(request, AuthConfirmTokenResponse.class);
        if (response.getStatusCode() != 200) {
            throw new IOException("Unexpected response for authConfirmToken: " + response);
        }
        return response.getResponseBody();
    }

    /**
     * Builds a new rest url for the given path. It might be that this connection's base url changes due to
     * the lookup request, so we use an reentrant lock here.
     * @param path path relative to base url
     * @return full url as string
     */
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

    @Override
    public String toString() {
        return new StringJoiner(", ", HomematicIPConnection.class.getSimpleName() + "[", "]")
                .add("uuid='" + uuid + "'")
                .add("accessPointId='" + accessPointId + "'")
                .add("transport=" + transport)
                .add("clientCharacteristics=" + clientCharacteristics)
                .add("urlREST='" + urlREST + "'")
                .add("urlWebSocket='" + urlWebSocket + "'")
                .toString();
    }
}
