package org.openhab.binding.homematicip.internal.model.event;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.home.Home;

import java.util.StringJoiner;

/**
 * State change event received from Homematic IP WebSocket
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-31
 */
public class PushEvent extends HomematicIPObject {

    private String pushEventType;
    private @Nullable Device device;
    private @Nullable Group group;
    private @Nullable Home home;

    public String getPushEventType() {
        return pushEventType;
    }

    public Device getDevice() {
        return device;
    }

    public Group getGroup() {
        return group;
    }

    public Home getHome() {
        return home;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PushEvent.class.getSimpleName() + "[", "]")
                .add("pushEventType='" + pushEventType + "'").add("device=" + device).add("group=" + group)
                .add("home=" + home).toString();
    }
}
