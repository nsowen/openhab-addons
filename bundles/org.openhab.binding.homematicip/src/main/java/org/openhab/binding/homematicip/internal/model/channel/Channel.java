package org.openhab.binding.homematicip.internal.model.channel;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;

/**
 * Channels used in group
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class Channel extends HomematicIPObject {

    private int channelIndex;
    private String deviceId;

    public int getChannelIndex() {
        return channelIndex;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
