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
import org.openhab.binding.homematicip.internal.model.channel.WallMountedThermostatProChannel;
import org.openhab.binding.homematicip.internal.model.common.ClimateControlDisplay;

import java.util.StringJoiner;

/**
 * HMIP-STHD (Temperature and Humidity Sensor with display - indoor)
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class TemperatureHumiditySensorDisplay extends Device<WallMountedThermostatProChannel> {

    public TemperatureHumiditySensorDisplay() {
        this.baseFunctionalChannelType = FunctionalChannelType.WALL_MOUNTED_THERMOSTAT_PRO_CHANNEL;
    }

    public float getTemperatureOffset() {
        return getBaseFunctionalChannel().map(WallMountedThermostatProChannel::getTemperatureOffset).orElse(0.0f);
    }

    public float getActualTemperature() {
        return getBaseFunctionalChannel().map(WallMountedThermostatProChannel::getActualTemperature).orElse(0.0f);
    }

    public int getHumidity() {
        return getBaseFunctionalChannel().map(WallMountedThermostatProChannel::getHumidity).orElse(0);
    }

    public double getVaporAmount() {
        return getBaseFunctionalChannel().map(WallMountedThermostatProChannel::getVaporAmount).orElse(0.0d);
    }

    public ClimateControlDisplay getDisplay() {
        return getBaseFunctionalChannel().map(WallMountedThermostatProChannel::getDisplay).orElse(ClimateControlDisplay.ACTUAL);
    }

    public float getSetPointTemperature() {
        return getBaseFunctionalChannel().map(WallMountedThermostatProChannel::getSetPointTemperature).orElse(0.0f);
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
                .toString();
    }

}
