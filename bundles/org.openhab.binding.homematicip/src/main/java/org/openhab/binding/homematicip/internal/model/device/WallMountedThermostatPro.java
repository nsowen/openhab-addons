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
import org.openhab.binding.homematicip.internal.model.channel.WallMountedThermostatProChannel;
import org.openhab.binding.homematicip.internal.model.common.ClimateControlDisplay;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * HMIP-WTH, HMIP-WTH-2 (Wall Thermostat with Humidity Sensor) / HMIP-BWTH (Brand Wall Thermostat with Humidity Sensor)
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class WallMountedThermostatPro extends AbstractOperationLockableDevice {

    public float getTemperatureOffset() {
        return getChannel().map(WallMountedThermostatProChannel::getTemperatureOffset).orElse(0.0f);
    }

    public float getActualTemperature() {
        return getChannel().map(WallMountedThermostatProChannel::getActualTemperature).orElse(0.0f);
    }

    public int getHumidity() {
        return getChannel().map(WallMountedThermostatProChannel::getHumidity).orElse(0);
    }

    public double getVaporAmount() {
        return getChannel().map(WallMountedThermostatProChannel::getVaporAmount).orElse(0.0d);
    }

    public ClimateControlDisplay getDisplay() {
        return getChannel().map(WallMountedThermostatProChannel::getDisplay).orElse(ClimateControlDisplay.ACTUAL);
    }

    public float getSetPointTemperature() {
        return getChannel().map(WallMountedThermostatProChannel::getSetPointTemperature).orElse(0.0f);
    }

    private Optional<WallMountedThermostatProChannel> getChannel() {
        return (Optional<WallMountedThermostatProChannel>) getFunctionalChannel(FunctionalChannelType.WALL_MOUNTED_THERMOSTAT_PRO_CHANNEL);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("id='" + getId() + "'")
                .add("homeId='" + getHomeId() + "'")
                .add("label='" + getLabel() + "'")
                .add("firmwareVersion='" + getFirmwareVersion() + "'")
                .add("temperatureOffset=" + getTemperatureOffset())
                .add("actualTemperature=" + getActualTemperature())
                .add("humidity=" + getHumidity())
                .add("vaporAmount=" + getVaporAmount())
                .add("setPointTemperature=" + getSetPointTemperature())
                .add("display=" + getDisplay())
                .add("operationLockActive=" + isOperationaLockActive())
                .toString();
    }

}
