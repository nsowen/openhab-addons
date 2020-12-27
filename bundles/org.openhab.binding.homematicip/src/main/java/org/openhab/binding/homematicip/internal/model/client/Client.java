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
package org.openhab.binding.homematicip.internal.model.client;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.common.ClientType;

import java.time.Instant;
import java.util.StringJoiner;

/**
 * Homematic IP API Client
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class Client extends HomematicIPObject {

    private ClientType clientType;
    private String id;
    private String label;
    private String homeId;
    private Instant createdAtTimestamp;
    private Instant lastSeenAtTimestamp;

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Client.class.getSimpleName() + "[", "]")
                .add("clientType=" + clientType)
                .add("id='" + id + "'")
                .add("label='" + label + "'")
                .add("homeId='" + homeId + "'")
                .add("createdAtTimestamp=" + createdAtTimestamp)
                .add("lastSeenAtTimestamp=" + lastSeenAtTimestamp)
                .toString();
    }
}
