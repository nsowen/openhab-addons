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
package org.openhab.binding.homematicip.internal.model.home.functional;

import org.openhab.binding.homematicip.internal.model.common.AbsenceType;
import org.openhab.binding.homematicip.internal.model.common.EcoDuration;
import org.openhab.binding.homematicip.internal.model.common.GroupType;

import java.time.Instant;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Indoor climate functional home
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class IndoorClimate extends FunctionalHome {

    private AbsenceType absenceType;
    private Instant absenceEndTime;
    private Map<GroupType,String> floorHeatingSpecificGroups;
    private float ecoTemperature;
    private boolean coolingEnabled;
    private EcoDuration ecoDuration;
    private boolean optimumStartStopEnabled;

    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }

    public Instant getAbsenceEndTime() {
        return absenceEndTime;
    }

    public void setAbsenceEndTime(Instant absenceEndTime) {
        this.absenceEndTime = absenceEndTime;
    }

    public Map<GroupType, String> getFloorHeatingSpecificGroups() {
        return floorHeatingSpecificGroups;
    }

    public void setFloorHeatingSpecificGroups(Map<GroupType, String> floorHeatingSpecificGroups) {
        this.floorHeatingSpecificGroups = floorHeatingSpecificGroups;
    }

    public float getEcoTemperature() {
        return ecoTemperature;
    }

    public void setEcoTemperature(float ecoTemperature) {
        this.ecoTemperature = ecoTemperature;
    }

    public boolean isCoolingEnabled() {
        return coolingEnabled;
    }

    public void setCoolingEnabled(boolean coolingEnabled) {
        this.coolingEnabled = coolingEnabled;
    }

    public EcoDuration getEcoDuration() {
        return ecoDuration;
    }

    public void setEcoDuration(EcoDuration ecoDuration) {
        this.ecoDuration = ecoDuration;
    }

    public boolean isOptimumStartStopEnabled() {
        return optimumStartStopEnabled;
    }

    public void setOptimumStartStopEnabled(boolean optimumStartStopEnabled) {
        this.optimumStartStopEnabled = optimumStartStopEnabled;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", IndoorClimate.class.getSimpleName() + "[", "]")
                .add("absenceType=" + absenceType)
                .add("absenceEndTime=" + absenceEndTime)
                .add("floorHeatingSpecificGroups=" + floorHeatingSpecificGroups)
                .add("ecoTemperature=" + ecoTemperature)
                .add("coolingEnabled=" + coolingEnabled)
                .add("ecoDuration=" + ecoDuration)
                .add("optimumStartStopEnabled=" + optimumStartStopEnabled)
                .toString();
    }
}
