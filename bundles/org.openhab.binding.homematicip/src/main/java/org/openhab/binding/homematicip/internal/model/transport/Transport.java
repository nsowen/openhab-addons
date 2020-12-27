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

import org.bouncycastle.jcajce.provider.digest.SHA512;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jetty.server.session.SessionHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Transport layer
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
@NonNullByDefault
public interface Transport {

    String CLIENTAUTH_HASH_SALT = "jiLpVitHvWnIGD1yo7MA";
    String LOOKUP_URL = "https://lookup.homematic.com:48335/getHost";
    String HEADER_CLIENTAUTH = "CLIENTAUTH";
    String HEADER_AUTHTOKEN = "AUTHTOKEN";
    String HEADER_VERSION = "VERSION";
    String API_VERSION = "12";

    void setClientAuth(String clientAuth);

    void setAuthToken(String authToken);

    boolean hasAuthToken();

    <T, V> CompletableFuture<Response<T,V>> postAsync(Request<T,V> request, Class<V> clazz, Executor executor);

    <T, V> Response<T,V> post(Request<T,V> request, Class<V> clazz) throws IOException;

    <U> void enableWebSocket(String wssUrl, WebSocketListener<U> listener);

    void disableWebSocket();

    default void setAccessPointId(String accessPointId) throws NoSuchAlgorithmException {
        setClientAuth(sha512(accessPointId, CLIENTAUTH_HASH_SALT).toUpperCase());
    }

    default String sha512(String text, String salt) throws NoSuchAlgorithmException {
        final var md = MessageDigest.getInstance("SHA-512");
        md.update(text.getBytes(StandardCharsets.UTF_8));
        final var bytes = md.digest(salt.getBytes(StandardCharsets.UTF_8));
        final var sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
