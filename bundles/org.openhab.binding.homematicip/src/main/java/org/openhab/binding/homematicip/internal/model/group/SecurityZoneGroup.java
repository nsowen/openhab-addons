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
package org.openhab.binding.homematicip.internal.model.group;

import java.util.List;

import org.openhab.binding.homematicip.internal.model.common.WindowState;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class SecurityZoneGroup extends Group {

    private boolean active;
    private boolean silent;
    private List<String> ignorableDevices;
    private WindowState windowState;
    private boolean motionDetected;
    private boolean sabotage;
    private boolean presenceDetected;

    public boolean isActive() {
        return active;
    }

    public boolean isSilent() {
        return silent;
    }

    public List<String> getIgnorableDevices() {
        return ignorableDevices;
    }

    public WindowState getWindowState() {
        return windowState;
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public boolean isSabotage() {
        return sabotage;
    }

    public boolean isPresenceDetected() {
        return presenceDetected;
    }
}
