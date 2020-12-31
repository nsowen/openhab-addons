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

/**
 * Listener for Homematic IP websocket data
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-24
 */
public interface WebSocketListener {
    void onWebSocketReceive(String data);

    void onWebSocketClose();

    void onWebSocketError(Throwable cause);
}
