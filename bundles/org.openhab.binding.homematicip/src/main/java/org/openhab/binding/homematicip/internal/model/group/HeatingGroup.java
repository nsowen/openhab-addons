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
package org.openhab.binding.homematicip.internal.model.group;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.common.ClimateControlMode;
import org.openhab.binding.homematicip.internal.model.common.HeatingCoolingProfile;
import org.openhab.binding.homematicip.internal.model.common.WindowState;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class HeatingGroup extends Group {

    private float windowOpenTemperature;
    private float setPointTemperature;
    private WindowState windowState;
    private float maxTemperature;
    private float minTemperature;
    private boolean cooling;
    private boolean partyMode;
    private ClimateControlMode controlMode = ClimateControlMode.AUTOMATIC;
    private boolean boostMode;
    private float boostDuration;
    private float actualTemperature;
    private int humidity;
    private boolean coolingAllowed;
    private boolean coolingIgnored;
    private boolean ecoAllowed;
    private boolean ecoIgnored;
    private boolean controllable;
    // todo private Object floorHeatingMode;
    private boolean humidityLimitEnabled;
    private int humidityLimitValue;
    private boolean externalClockEnabled;
    private float externalClockHeatingTemperature;
    private float externalClockCoolingTemperature;
    private Map<String, HeatingCoolingProfile> profiles;
    private String activeProfile;
    private boolean dutyCycle;
    private boolean lowBat;
    private float valvePosition;
    private boolean heatingFailureSupported;
    private boolean valveSilentModeEnabled;
    private boolean valveSilentModeSupported;
    private Instant lastSetPointReachedTimestamp;
    private Instant lastSetPointUpdatedTimestamp;

    public Optional<HeatingCoolingProfile> getActiveProfile() {
        return profiles.values().stream().filter(p -> p.getId().equals(activeProfile)).findAny();
    }

    public float getWindowOpenTemperature() {
        return windowOpenTemperature;
    }

    public float getSetPointTemperature() {
        return setPointTemperature;
    }

    public WindowState getWindowState() {
        return windowState;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public boolean isCooling() {
        return cooling;
    }

    public boolean isPartyMode() {
        return partyMode;
    }

    public ClimateControlMode getControlMode() {
        return controlMode;
    }

    public boolean isBoostMode() {
        return boostMode;
    }

    public float getBoostDuration() {
        return boostDuration;
    }

    public float getActualTemperature() {
        return actualTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public boolean isCoolingAllowed() {
        return coolingAllowed;
    }

    public boolean isCoolingIgnored() {
        return coolingIgnored;
    }

    public boolean isEcoAllowed() {
        return ecoAllowed;
    }

    public boolean isEcoIgnored() {
        return ecoIgnored;
    }

    public boolean isControllable() {
        return controllable;
    }

    public boolean isHumidityLimitEnabled() {
        return humidityLimitEnabled;
    }

    public int getHumidityLimitValue() {
        return humidityLimitValue;
    }

    public boolean isExternalClockEnabled() {
        return externalClockEnabled;
    }

    public float getExternalClockHeatingTemperature() {
        return externalClockHeatingTemperature;
    }

    public float getExternalClockCoolingTemperature() {
        return externalClockCoolingTemperature;
    }

    public Map<String, HeatingCoolingProfile> getProfiles() {
        return profiles;
    }

    public boolean isDutyCycle() {
        return dutyCycle;
    }

    public boolean isLowBat() {
        return lowBat;
    }

    public float getValvePosition() {
        return valvePosition;
    }

    public boolean isHeatingFailureSupported() {
        return heatingFailureSupported;
    }

    public boolean isValveSilentModeEnabled() {
        return valveSilentModeEnabled;
    }

    public boolean isValveSilentModeSupported() {
        return valveSilentModeSupported;
    }

    public Instant getLastSetPointReachedTimestamp() {
        return lastSetPointReachedTimestamp;
    }

    public Instant getLastSetPointUpdatedTimestamp() {
        return lastSetPointUpdatedTimestamp;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HeatingGroup.class.getSimpleName() + "[", "]")
                .add("windowOpenTemperature=" + windowOpenTemperature).add("setPointTemperature=" + setPointTemperature)
                .add("windowState=" + windowState).add("maxTemperature=" + maxTemperature)
                .add("minTemperature=" + minTemperature).add("cooling=" + cooling).add("partyMode=" + partyMode)
                .add("controlMode=" + controlMode).add("boostMode=" + boostMode).add("boostDuration=" + boostDuration)
                .add("actualTemperature=" + actualTemperature).add("humidity=" + humidity)
                .add("coolingAllowed=" + coolingAllowed).add("coolingIgnored=" + coolingIgnored)
                .add("ecoAllowed=" + ecoAllowed).add("ecoIgnored=" + ecoIgnored).add("controllable=" + controllable)
                .add("humidityLimitEnabled=" + humidityLimitEnabled).add("humidityLimitValue=" + humidityLimitValue)
                .add("externalClockEnabled=" + externalClockEnabled)
                .add("externalClockHeatingTemperature=" + externalClockHeatingTemperature)
                .add("externalClockCoolingTemperature=" + externalClockCoolingTemperature).add("profiles=" + profiles)
                .add("activeProfile='" + activeProfile + "'").add("dutyCycle=" + dutyCycle).add("lowBat=" + lowBat)
                .add("valvePosition=" + valvePosition).add("heatingFailureSupported=" + heatingFailureSupported)
                .add("valveSilentModeEnabled=" + valveSilentModeEnabled)
                .add("valveSilentModeSupported=" + valveSilentModeSupported)
                .add("lastSetPointReachedTimestamp=" + lastSetPointReachedTimestamp)
                .add("lastSetPointUpdatedTimestamp=" + lastSetPointUpdatedTimestamp).toString();
    }
}
