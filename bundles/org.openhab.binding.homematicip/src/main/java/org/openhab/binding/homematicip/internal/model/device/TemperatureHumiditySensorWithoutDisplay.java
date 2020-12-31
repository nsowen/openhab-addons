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
package org.openhab.binding.homematicip.internal.model.device;

import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.channel.WallMountedThermostatWithoutDisplayChannel;

/**
 * HMIP-STH (Temperature and Humidity Sensor without display - indoor)
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class TemperatureHumiditySensorWithoutDisplay extends Device<WallMountedThermostatWithoutDisplayChannel> {

    public TemperatureHumiditySensorWithoutDisplay() {
        this.baseFunctionalChannelType = FunctionalChannelType.WALL_MOUNTED_THERMOSTAT_WITHOUT_DISPLAY_CHANNEL;
    }

    public float getTemperatureOffset() {
        return getBaseFunctionalChannel().map(WallMountedThermostatWithoutDisplayChannel::getTemperatureOffset)
                .orElse(0.0f);
    }

    public float getActualTemperature() {
        return getBaseFunctionalChannel().map(WallMountedThermostatWithoutDisplayChannel::getActualTemperature)
                .orElse(0.0f);
    }

    public int getHumidity() {
        return getBaseFunctionalChannel().map(WallMountedThermostatWithoutDisplayChannel::getHumidity).orElse(0);
    }

    public double getVaporAmount() {
        return getBaseFunctionalChannel().map(WallMountedThermostatWithoutDisplayChannel::getVaporAmount).orElse(0.0d);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TemperatureHumiditySensorWithoutDisplay.class.getSimpleName() + "[", "]")
                .add("id='" + getId() + "'").add("homeId='" + getHomeId() + "'").add("label='" + getLabel() + "'")
                .add("firmwareVersion='" + getFirmwareVersion() + "'")
                .add("temperatureOffset=" + getTemperatureOffset()).add("actualTemperature=" + getActualTemperature())
                .add("humidity=" + getHumidity()).add("vaporAmount=" + getVaporAmount()).toString();
    }
}
