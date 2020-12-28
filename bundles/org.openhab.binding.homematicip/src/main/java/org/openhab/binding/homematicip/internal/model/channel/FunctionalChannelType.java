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
package org.openhab.binding.homematicip.internal.model.channel;

/**
 * Homematic IP functional channels
 *
 * @author Nils Sowen (n.sowen@2scale.net)
 * @since 2020-12-24
 */
public enum FunctionalChannelType {
    FUNCTIONAL_CHANNEL(FunctionalChannel.class),
    ACCELERATION_SENSOR_CHANNEL(AccelerationSensorChannel.class),
    ACCESS_CONTROLLER_CHANNEL(AccessControllerChannel.class),
    ALARM_SIREN_CHANNEL(AlarmSirenChannel.class),
    ANALOG_OUTPUT_CHANNEL(AnalogOutputChannel.class),
    ANALOG_ROOM_CONTROL_CHANNEL(AnalogRoomControlChannel.class),
    BLIND_CHANNEL(BlindChannel.class),
    CHANGE_OVER_CHANNEL(ChangeOverChannel.class),
    CLIMATE_SENSOR_CHANNEL(ClimateSensorChannel.class),
    CONTACT_INTERFACE_CHANNEL(ContactInterfaceChannel.class),
    DEHUMIDIFIER_DEMAND_CHANNEL(DehumidifierDemandChannel.class),
    DEVICE_BASE(DeviceBaseChannel.class),
    DEVICE_BASE_FLOOR_HEATING(DeviceBaseFloorHeatingChannel.class),
    DEVICE_GLOBAL_PUMP_CONTROL(DeviceGlobalPumpControlChannel.class),
    DEVICE_INCORRECT_POSITIONED(DeviceIncorrectPositionedChannel.class),
    DEVICE_OPERATIONLOCK(DeviceOperationLockChannel.class),
    DEVICE_PERMANENT_FULL_RX(DevicePermanentFullRxChannel.class),
    DEVICE_RECHARGEABLE_WITH_SABOTAGE(DeviceRechargeableWithSabotage.class),
    DEVICE_SABOTAGE(DeviceSabotageChannel.class),
    DIMMER_CHANNEL(DimmerChannel.class),
    DOOR_CHANNEL(DoorChannel.class),
    FLOOR_TERMINAL_BLOCK_CHANNEL(FloorTeminalBlockChannel.class),
    FLOOR_TERMINAL_BLOCK_LOCAL_PUMP_CHANNEL(FloorTerminalBlockLocalPumpChannel.class),
    FLOOR_TERMINAL_BLOCK_MECHANIC_CHANNEL(FloorTerminalBlockMechanicChannel.class),
    GENERIC_INPUT_CHANNEL(GenericInputChannel.class),
    HEAT_DEMAND_CHANNEL(HeatDemandChannel.class),
    HEATING_THERMOSTAT_CHANNEL(HeatingThermostatChannel.class),
    INTERNAL_SWITCH_CHANNEL(InternalSwitchChannel.class),
    LIGHT_SENSOR_CHANNEL(LightSensorChannel.class),
    MAINS_FAILURE_CHANNEL(MainsFailureChannel.class),
    MOTION_DETECTION_CHANNEL(MotionDetectionChannel.class),
    MULTI_MODE_INPUT_CHANNEL(MultiModeInputChannel.class),
    MULTI_MODE_INPUT_BLIND_CHANNEL(MultiModeInputBlindChannel.class),
    MULTI_MODE_INPUT_SWITCH_CHANNEL(MultiModeInputSwitchChannel.class),
    NOTIFICATION_LIGHT_CHANNEL(NotificationLightChannel.class),
    PASSAGE_DETECTOR_CHANNEL(PassageDetectorChannel.class),
    PRESENCE_DETECTION_CHANNEL(PresenceDetectionChannel.class),
    ROTARY_HANDLE_CHANNEL(RotaryHandleChannel.class),
    SHADING_CHANNEL(ShadingChannel.class),
    SHUTTER_CHANNEL(ShutterChannel.class),
    SHUTTER_CONTACT_CHANNEL(ShutterContactChannel.class),
    SINGLE_KEY_CHANNEL(SingleKeyChannel.class),
    SMOKE_DETECTOR_CHANNEL(SmokeDetectorChannel.class),
    SWITCH_CHANNEL(SwitchChannel.class),
    SWITCH_MEASURING_CHANNEL(SwitchMeasuringChannel.class),
    TILT_VIBRATION_SENSOR_CHANNEL(TiltVibrationSensorChannel.class),
    WALL_MOUNTED_THERMOSTAT_PRO_CHANNEL(WallMountedThermostatProChannel.class),
    WALL_MOUNTED_THERMOSTAT_WITHOUT_DISPLAY_CHANNEL(WallMountedThermostatWithoutDisplayChannel.class),
    WATER_SENSOR_CHANNEL(WaterSensorChannel.class),
    WEATHER_SENSOR_CHANNEL(WeatherSensorChannel.class),
    WEATHER_SENSOR_PLUS_CHANNEL(WeatherSensorPlusChannel.class),
    WEATHER_SENSOR_PRO_CHANNEL(WeatherSensorProChannel.class);

    private final Class<? extends FunctionalChannel> clazz;

    FunctionalChannelType(Class<? extends FunctionalChannel> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends FunctionalChannel> getClazz() {
        return clazz;
    }
}
