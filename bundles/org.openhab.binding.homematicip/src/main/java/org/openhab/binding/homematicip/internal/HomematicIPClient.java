package org.openhab.binding.homematicip.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.homematicip.internal.discovery.HomematicIPDiscoveryService;

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
}
