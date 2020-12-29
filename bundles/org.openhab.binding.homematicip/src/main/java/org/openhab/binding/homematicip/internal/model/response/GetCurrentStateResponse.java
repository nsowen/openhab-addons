package org.openhab.binding.homematicip.internal.model.response;

import java.util.*;

import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.client.Client;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.home.Home;

/**
 * New class.
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-26
 */
public class GetCurrentStateResponse extends HomematicIPObject {

    private Home home;
    private Map<String, Group> groups;
    private Map<String, Device> devices;
    private Map<String, Client> clients;

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public List<Group> getGroups() {
        return groups != null ? new ArrayList<>(groups.values()) : Collections.emptyList();
    }

    public List<Device> getDevices() {
        return devices != null ? new ArrayList<>(devices.values()) : Collections.emptyList();
    }

    public void setGroups(Map<String, Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GetCurrentStateResponse.class.getSimpleName() + "[", "]").add("home=" + home)
                .add("devices=" + devices).add("groups=" + groups).add("clients=" + clients).toString();
    }
}
