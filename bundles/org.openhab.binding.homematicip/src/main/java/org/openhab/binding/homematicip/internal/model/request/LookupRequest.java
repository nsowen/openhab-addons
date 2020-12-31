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
package org.openhab.binding.homematicip.internal.model.request;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;

/**
 * Lookup access point using HmIP-API
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-25
 */
public class LookupRequest extends HomematicIPObject {

    private ClientCharacteristics clientCharacteristics;
    private String id;

    public LookupRequest(String accessPointId, ClientCharacteristics clientCharacteristics) {
        this.id = accessPointId;
        this.clientCharacteristics = clientCharacteristics;
    }

    public ClientCharacteristics getClientCharacteristics() {
        return clientCharacteristics;
    }

    public void setClientCharacteristics(ClientCharacteristics clientCharacteristics) {
        this.clientCharacteristics = clientCharacteristics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
