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
package org.openhab.binding.homematicip.internal.model.device;

import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.channel.HeatingThermostatChannel;
import org.openhab.binding.homematicip.internal.model.common.ValveState;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * HMIP-eTRV (Radiator Thermostat)
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class HeatingThermostat extends AbstractOperationLockableDevice {

    public float getTemperatureOffset() {
        return getChannel().map(HeatingThermostatChannel::getTemperatureOffset).orElse(0.0f);
    }

    public float getValvePosition() {
        return getChannel().map(HeatingThermostatChannel::getValvePosition).orElse(0.0f);
    }

    public ValveState getValveState() {
        return getChannel().map(HeatingThermostatChannel::getValveState).orElse(ValveState.ERROR_POSITION);
    }

    public float getSetPointTemperature() {
        return getChannel().map(HeatingThermostatChannel::getSetPointTemperature).orElse(0.0f);
    }

    public float getValveActualTemperature() {
        return getChannel().map(HeatingThermostatChannel::getValveActualTemperature).orElse(0.0f);
    }

    public boolean isAutomaticValveAdaptionNeeded() {
        return getChannel().map(HeatingThermostatChannel::isAutomaticValveAdaptionNeeded).orElse(false);
    }

    private Optional<HeatingThermostatChannel> getChannel() {
        return (Optional<HeatingThermostatChannel>) getFunctionalChannel(FunctionalChannelType.HEATING_THERMOSTAT_CHANNEL);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HeatingThermostat.class.getSimpleName() + "[", "]")
                .add("operationLockActive=" + isOperationaLockActive())
                .add("temperatureOffset=" + getTemperatureOffset())
                .add("valvePosition=" + getValvePosition())
                .add("valveState=" + getValveState())
                .add("setPointTemperature=" + getSetPointTemperature())
                .add("valveActualTemperature=" + getValveActualTemperature())
                .add("automaticValveAdaptionNeeded=" + isAutomaticValveAdaptionNeeded())
                .toString();
    }
}
