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

import java.util.StringJoiner;

import org.openhab.binding.homematicip.internal.model.channel.ClimateSensorChannel;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;

/**
 * HMIP-STHO (Temperature and Humidity Sensor outdoor)
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class TemperatureHumiditySensorOutdoor extends Device<ClimateSensorChannel> {

    public TemperatureHumiditySensorOutdoor() {
        this.baseFunctionalChannelType = FunctionalChannelType.CLIMATE_SENSOR_CHANNEL;
    }

    public float getActualTemperature() {
        return getBaseFunctionalChannel().map(ClimateSensorChannel::getActualTemperature).orElse(0.0f);
    }

    public int getHumidity() {
        return getBaseFunctionalChannel().map(ClimateSensorChannel::getHumidity).orElse(0);
    }

    public double getVaporAmount() {
        return getBaseFunctionalChannel().map(ClimateSensorChannel::getVaporAmount).orElse(0.0d);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]").add("id='" + getId() + "'")
                .add("homeId='" + getHomeId() + "'").add("label='" + getLabel() + "'")
                .add("firmwareVersion='" + getFirmwareVersion() + "'")
                .add("actualTemperature=" + getActualTemperature()).add("humidity=" + getHumidity())
                .add("vaporAmount=" + getVaporAmount()).toString();
    }
}
