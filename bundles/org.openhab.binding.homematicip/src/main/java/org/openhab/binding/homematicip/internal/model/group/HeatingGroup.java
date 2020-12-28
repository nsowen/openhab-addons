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

import org.openhab.binding.homematicip.internal.model.common.ClimateControlMode;
import org.openhab.binding.homematicip.internal.model.common.HeatingCoolingProfile;

/**
 * Group-specific implementation
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class HeatingGroup extends Group {

    private float windowOpenTemperature;
    private float setPointTemperature;
    // todo private Object windowState;
    private float maxTemperature;
    private float minTemperature;
    private boolean cooling;
    private boolean partyMode;
    private ClimateControlMode controlMode = ClimateControlMode.AUTOMATIC;
    // private Object activeProfile;
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
    // todo private int humidityLimitValue;
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
}
