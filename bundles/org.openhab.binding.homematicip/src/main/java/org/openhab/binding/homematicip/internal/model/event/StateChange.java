package org.openhab.binding.homematicip.internal.model.event;

import java.util.*;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.home.Home;

/**
 * State change event received from Homematic IP WebSocket
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-31
 */
public class StateChange extends HomematicIPObject {

    private Map<String, PushEvent> events = Collections.emptyMap();

    public Map<String, PushEvent> getEvents() {
        return new TreeMap(events);
    }

    public List<PushEvent> getEventList() {
        return new ArrayList(getEvents().values());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StateChange.class.getSimpleName() + "[", "]")
                .add("events=" + events)
                .toString();
    }
}
