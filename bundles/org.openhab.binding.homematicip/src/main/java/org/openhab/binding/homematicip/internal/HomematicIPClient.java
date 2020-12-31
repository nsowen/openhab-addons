package org.openhab.binding.homematicip.internal;

import java.util.Optional;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.homematicip.internal.discovery.HomematicIPDiscoveryService;
import org.openhab.binding.homematicip.internal.handler.ThingStatusListener;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.home.Home;

/**
 * Access to the Homematic IP system
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-28
 */
@NonNullByDefault
public interface HomematicIPClient {

    /**
     * Register {@link HomematicIPDiscoveryService} to bridge handler
     *
     * @param listener the discovery service
     * @return {@code true} if the new discovery service is accepted
     */
    boolean registerDiscoveryListener(HomematicIPDiscoveryService listener);

    /**
     * Unregister {@link HomematicIPDiscoveryService} from bridge handler
     *
     * @return {@code true} if the discovery service was removed
     */
    boolean unregisterDiscoveryListener();

    /**
     * Register a thing status listener. Each ThingHandler registers for updates
     * with the bridge.
     *
     * @param thingStatusListener the light status listener
     * @return {@code true} if the collection of listeners has changed as a result of this call
     */
    boolean registerThingStatusListener(ThingStatusListener thingStatusListener);

    /**
     * Unregister a thing status listener.
     *
     * @param thingStatusListener the thing status listener
     * @return {@code true} if the collection of listeners has changed as a result of this call
     */
    boolean unregisterThingStatusListener(ThingStatusListener thingStatusListener);

    Optional<Group> getGroupById(String id);

    Optional<Device> getDeviceById(String id);

    Optional<Home> getHome();
}
