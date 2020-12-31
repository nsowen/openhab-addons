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

import org.openhab.binding.homematicip.internal.model.common.SmokeDetectorAlarmType;
import org.openhab.binding.homematicip.internal.model.common.WindowState;

/**
 * Security group
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class SecurityGroup extends Group {

    private WindowState windowState;
    private boolean motionDetected;
    private boolean presenceDetected;
    private Boolean sabotage;
    private SmokeDetectorAlarmType smokeDetectorAlarmType;
    private boolean dutyCycle;
    private boolean lowBat;
    private boolean moistureDetected;
    private boolean powerMainsFailure;
    private boolean waterlevelDetected;

    public WindowState getWindowState() {
        return windowState;
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public boolean isPresenceDetected() {
        return presenceDetected;
    }

    public Boolean getSabotage() {
        return sabotage;
    }

    public SmokeDetectorAlarmType getSmokeDetectorAlarmType() {
        return smokeDetectorAlarmType;
    }

    public boolean isDutyCycle() {
        return dutyCycle;
    }

    public boolean isLowBat() {
        return lowBat;
    }

    public boolean isMoistureDetected() {
        return moistureDetected;
    }

    public boolean isPowerMainsFailure() {
        return powerMainsFailure;
    }

    public boolean isWaterlevelDetected() {
        return waterlevelDetected;
    }
}
