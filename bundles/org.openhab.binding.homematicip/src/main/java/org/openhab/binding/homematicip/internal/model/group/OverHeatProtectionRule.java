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

/**
 * Group-specific implementation
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class OverHeatProtectionRule extends Group {

    protected float temperatureLowerThreshold;
    protected float temperatureUpperThreshold;
    protected float targetShutterLevel;
    protected float targetSlatsLevel;
    protected int startHour;
    protected int startMinute;
    protected int startSunrise;
    protected int endHour;
    protected int endMinute;
    protected int endSunset;

    public float getTemperatureLowerThreshold() {
        return temperatureLowerThreshold;
    }

    public float getTemperatureUpperThreshold() {
        return temperatureUpperThreshold;
    }

    public float getTargetShutterLevel() {
        return targetShutterLevel;
    }

    public float getTargetSlatsLevel() {
        return targetSlatsLevel;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getStartSunrise() {
        return startSunrise;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public int getEndSunset() {
        return endSunset;
    }
}
