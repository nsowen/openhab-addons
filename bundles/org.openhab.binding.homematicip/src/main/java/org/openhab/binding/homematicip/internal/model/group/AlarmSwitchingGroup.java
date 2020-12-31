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

import java.time.Instant;

import org.openhab.binding.homematicip.internal.model.common.AcousticAlarmSignal;
import org.openhab.binding.homematicip.internal.model.common.OpticalAlarmSignal;
import org.openhab.binding.homematicip.internal.model.common.SmokeDetectorAlarmType;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class AlarmSwitchingGroup extends Group {

    private boolean on;
    private float dimLevel;
    private Instant onTime;
    private AcousticAlarmSignal signalAcoustic;
    private OpticalAlarmSignal signalOptical;
    private SmokeDetectorAlarmType smokeDetectorAlarmType;
    private boolean acousticFeedbackEnabled;

    public boolean isOn() {
        return on;
    }

    public float getDimLevel() {
        return dimLevel;
    }

    public Instant getOnTime() {
        return onTime;
    }

    public AcousticAlarmSignal getSignalAcoustic() {
        return signalAcoustic;
    }

    public OpticalAlarmSignal getSignalOptical() {
        return signalOptical;
    }

    public SmokeDetectorAlarmType getSmokeDetectorAlarmType() {
        return smokeDetectorAlarmType;
    }

    public boolean isAcousticFeedbackEnabled() {
        return acousticFeedbackEnabled;
    }
}
