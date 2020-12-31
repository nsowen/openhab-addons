package org.openhab.binding.homematicip.internal.model.channel;

import org.openhab.binding.homematicip.internal.model.common.AccelerationSensorMode;
import org.openhab.binding.homematicip.internal.model.common.AccelerationSensorNeutralPosition;
import org.openhab.binding.homematicip.internal.model.common.AccelerationSensorSensitivity;
import org.openhab.binding.homematicip.internal.model.common.NotificationSoundType;

/**
 * New class.
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class AccelerationSensorChannel extends FunctionalChannel {

    protected double accelerationSensorEventFilterPeriod = 100.0;
    protected AccelerationSensorMode accelerationSensorMode = AccelerationSensorMode.ANY_MOTION;
    protected AccelerationSensorNeutralPosition accelerationSensorNeutralPosition = AccelerationSensorNeutralPosition.HORIZONTAL;
    protected AccelerationSensorSensitivity accelerationSensorSensitivity = AccelerationSensorSensitivity.SENSOR_RANGE_2G;
    protected int accelerationSensorTriggerAngle = 0;
    protected boolean accelerationSensorTriggered;
    protected NotificationSoundType notificationSoundTypeHighToLow = NotificationSoundType.SOUND_NO_SOUND;
    protected NotificationSoundType notificationSoundTypeLowToHigh = NotificationSoundType.SOUND_NO_SOUND;
}
