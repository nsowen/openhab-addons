package org.openhab.binding.homematicip.internal.model.request;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;

/**
 * Sets the signal brightnesss
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-30
 */
public class SetSignalBrightnessRequest extends HomematicIPObject {

    private int channelIndex;
    private String deviceId;
    private float signalBrightness; // 0.0 - 1.0

    public SetSignalBrightnessRequest(String id, float brightness, int channelIndex) {
        this.channelIndex = channelIndex;
        this.deviceId = id;
        this.signalBrightness = brightness;
    }

    public int getChannelIndex() {
        return channelIndex;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public float getSignalBrightness() {
        return signalBrightness;
    }
}
