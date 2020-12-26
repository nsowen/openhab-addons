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

/**
 * Listener for Homematic IP websocket data
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-24
 */
public interface WebSocketListener<T> {

    void onReceive(T data);

}
