package org.openhab.binding.homematicip.internal.discovery;

import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.config.discovery.AbstractDiscoveryService;
import org.openhab.core.config.discovery.DiscoveryService;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerService;

/**
 * Discovers new things connected to the HomematicIP bridge
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-28
 */
public class HomematicIPDiscoveryService extends AbstractDiscoveryService
        implements DiscoveryService, ThingHandlerService {

    public HomematicIPDiscoveryService(@Nullable Set<ThingTypeUID> supportedThingTypes, int timeout)
            throws IllegalArgumentException {
        super(supportedThingTypes, timeout);
    }

    @Override
    protected void startScan() {
    }

    @Override
    public void setThingHandler(ThingHandler thingHandler) {
    }

    @Override
    public @Nullable ThingHandler getThingHandler() {
        return null;
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }
}
