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

import org.openhab.binding.homematicip.internal.model.channel.AnalogRoomControlChannel;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.channel.WallMountedThermostatProChannel;
import org.openhab.binding.homematicip.internal.model.common.ClimateControlDisplay;

import java.util.StringJoiner;

/**
 * ALPHA-IP-RBG    (Alpha IP Wall Thermostat Display)
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class RoomControlDeviceAnalog extends Device<AnalogRoomControlChannel> {

    public RoomControlDeviceAnalog() {
        this.baseFunctionalChannelType = FunctionalChannelType.ANALOG_ROOM_CONTROL_CHANNEL;
    }

    public float getTemperatureOffset() {
        return getBaseFunctionalChannel().map(AnalogRoomControlChannel::getTemperatureOffset).orElse(0.0f);
    }

    public float getActualTemperature() {
        return getBaseFunctionalChannel().map(AnalogRoomControlChannel::getActualTemperature).orElse(0.0f);
    }

    public float getSetPointTemperature() {
        return getBaseFunctionalChannel().map(AnalogRoomControlChannel::getSetPointTemperature).orElse(0.0f);
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
                .add("setPointTemperature=" + getSetPointTemperature())
                .toString();
    }

}
