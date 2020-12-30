package org.openhab.binding.homematicip.internal.model.channel;

import java.util.StringJoiner;

/**
 * Access controller channel
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class AccessControllerChannel extends DeviceBaseChannel {

    private float dutyCycleLevel;
    private int accessPointPriority;
    private float signalBrightness;

    public float getDutyCycleLevel() {
        return dutyCycleLevel;
    }

    public int getAccessPointPriority() {
        return accessPointPriority;
    }

    public float getSignalBrightness() {
        return signalBrightness;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccessControllerChannel.class.getSimpleName() + "[", "]")
                .add("dutyCycleLevel=" + dutyCycleLevel).add("accessPointPriority=" + accessPointPriority)
                .add("signalBrightness=" + signalBrightness).add("unreach=" + unreach).add("lowBat=" + lowBat)
                .add("routerModuleSupported=" + routerModuleSupported).add("routerModuleEnabled=" + routerModuleEnabled)
                .add("rssiDeviceValue=" + rssiDeviceValue).add("rssiPeerValue=" + rssiPeerValue)
                .add("dutyCycle=" + dutyCycle).add("configPending=" + configPending)
                .add("supportedOptionalFeatures=" + supportedOptionalFeatures).add("index=" + index)
                .add("groupIndex=" + groupIndex).add("label='" + label + "'")
                .add("functionalChannelType=" + functionalChannelType).add("groups=" + groups).toString();
    }
}
