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
 * Request authorization for new app
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-24
 */
public class AuthConfirmTokenRequest extends HomematicIPObject {

    private String deviceId;
    private String authToken;

    public AuthConfirmTokenRequest(String uuid, String authToken) {
        this.deviceId = uuid;
        this.authToken = authToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
