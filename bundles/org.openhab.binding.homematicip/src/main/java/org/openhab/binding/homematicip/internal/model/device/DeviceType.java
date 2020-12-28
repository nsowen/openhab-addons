package org.openhab.binding.homematicip.internal.model.device;

import org.openhab.binding.homematicip.internal.model.group.*;

/**
 * Device type mapping
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public enum DeviceType {

    ACCELERATION_SENSOR(AccelerationSensor.class),
    ALARM_SIREN_INDOOR(AlarmSirenIndoor.class),
    ALARM_SIREN_OUTDOOR(AlarmSirenOutdoor.class),
    BLIND_MODULE(BlindModule.class),
    BRAND_BLIND(BrandBlind.class),
    BRAND_DIMMER(BrandDimmer.class),
    BRAND_PUSH_BUTTON(BrandPushButton.class),
    BRAND_SHUTTER(FullFlushShutter.class),
    BRAND_SWITCH_MEASURING(BrandSwitchMeasuring.class),
    BRAND_SWITCH_NOTIFICATION_LIGHT(BrandSwitchNotificationLight.class),
    BRAND_WALL_MOUNTED_THERMOSTAT(WallMountedThermostatPro.class),
    DIN_RAIL_BLIND_4(DinRailBlind4.class),
    DIN_RAIL_SWITCH_4(DinRailSwitch4.class),
    FLOOR_TERMINAL_BLOCK_10(FloorTerminalBlock10.class),
    FLOOR_TERMINAL_BLOCK_12(FloorTerminalBlock12.class),
    FLOOR_TERMINAL_BLOCK_6(FloorTerminalBlock6.class),
    FULL_FLUSH_BLIND(FullFlushBlind.class),
    FULL_FLUSH_CONTACT_INTERFACE(FullFlushContactInterface.class),
    FULL_FLUSH_CONTACT_INTERFACE_6(FullFlushContactInterface6.class),
    FULL_FLUSH_DIMMER(FullFlushDimmer.class),
    FULL_FLUSH_INPUT_SWITCH(FullFlushInputSwitch.class),
    FULL_FLUSH_SHUTTER(FullFlushShutter.class),
    FULL_FLUSH_SWITCH_MEASURING(FullFlushSwitchMeasuring.class),
    HEATING_SWITCH_2(HeatingSwitch2.class),
    HEATING_THERMOSTAT(HeatingThermostat.class),
    HEATING_THERMOSTAT_COMPACT(HeatingThermostatCompact.class),
    HOME_CONTROL_ACCESS_POINT(HomeControlAccessPoint.class),
    HOERMANN_DRIVES_MODULE(HoermannDrivesModule.class),
    KEY_REMOTE_CONTROL_4(KeyRemoteControl4.class),
    KEY_REMOTE_CONTROL_ALARM(KeyRemoteControlAlarm.class),
    LIGHT_SENSOR(LightSensor.class),
    MOTION_DETECTOR_INDOOR(MotionDetectorIndoor.class),
    MOTION_DETECTOR_OUTDOOR(MotionDetectorOutdoor.class),
    MOTION_DETECTOR_PUSH_BUTTON(MotionDetectorPushButton.class),
    MULTI_IO_BOX(MultiIOBox.class),
    OPEN_COLLECTOR_8_MODULE(OpenCollector8Module.class),
    PASSAGE_DETECTOR(PassageDetector.class),
    PLUGABLE_SWITCH(PlugableSwitch.class),
    PLUGABLE_SWITCH_MEASURING(PlugableSwitchMeasuring.class),
    PLUGGABLE_DIMMER(PluggableDimmer.class),
    PLUGGABLE_MAINS_FAILURE_SURVEILLANCE(PluggableMainsFailureSurveillance.class),
    PRESENCE_DETECTOR_INDOOR(PresenceDetectorIndoor.class),
    PRINTED_CIRCUIT_BOARD_SWITCH_2(PrintedCircuitBoardSwitch2.class),
    PRINTED_CIRCUIT_BOARD_SWITCH_BATTERY(PrintedCircuitBoardSwitchBattery.class),
    PUSH_BUTTON(PushButton.class),
    PUSH_BUTTON_6(PushButton6.class),
    REMOTE_CONTROL_8(RemoteControl8.class),
    REMOTE_CONTROL_8_MODULE(RemoteControl8Module.class),
    ROOM_CONTROL_DEVICE(RoomControlDevice.class),
    ROOM_CONTROL_DEVICE_ANALOG(RoomControlDeviceAnalog.class),
    ROTARY_HANDLE_SENSOR(RotaryHandleSensor.class),
    SHUTTER_CONTACT(ShutterContact.class),
    SHUTTER_CONTACT_INTERFACE(ContactInterface.class),
    SHUTTER_CONTACT_INVISIBLE(ShutterContact.class),
    SHUTTER_CONTACT_MAGNETIC(ShutterContactMagnetic.class),
    SHUTTER_CONTACT_OPTICAL_PLUS(ShutterContactOpticalPlus.class),
    SMOKE_DETECTOR(SmokeDetector.class),
    TEMPERATURE_HUMIDITY_SENSOR(TemperatureHumiditySensorWithoutDisplay.class),
    TEMPERATURE_HUMIDITY_SENSOR_DISPLAY(TemperatureHumiditySensorDisplay.class),
    TEMPERATURE_HUMIDITY_SENSOR_OUTDOOR(TemperatureHumiditySensorOutdoor.class),
    TILT_VIBRATION_SENSOR(TiltVibrationSensor.class),
    TORMATIC_MODULE(GarageDoorModuleTormatic.class),
    WALL_MOUNTED_THERMOSTAT_PRO(WallMountedThermostatPro.class),
    WALL_MOUNTED_THERMOSTAT_BASIC_HUMIDITY(WallMountedThermostatBasicHumidity.class),
    WATER_SENSOR(WaterSensor.class),
    WEATHER_SENSOR(WeatherSensor.class),
    WEATHER_SENSOR_PLUS(WeatherSensorPlus.class),
    WEATHER_SENSOR_PRO(WeatherSensorPro.class),
    WIRED_DIMMER_3(WiredDimmer3.class),
    WIRED_INPUT_32(WiredInput32.class),
    WIRED_SWITCH_8(WiredSwitch8.class);

    private final Class<? extends Device> clazz;

    DeviceType(Class<? extends Device> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Device> getClazz() {
        return clazz;
    }
}
