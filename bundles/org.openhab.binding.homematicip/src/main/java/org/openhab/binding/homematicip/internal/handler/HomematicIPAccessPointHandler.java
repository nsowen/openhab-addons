package org.openhab.binding.homematicip.internal.handler;

import static org.openhab.binding.homematicip.internal.HomematicIPBindingConstants.THING_TYPE_ACCESSPOINT;

import java.io.IOException;
import java.util.Set;

import org.openhab.binding.homematicip.internal.model.device.HomeControlAccessPoint;
import org.openhab.core.library.types.IncreaseDecreaseType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * New class.
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-30
 */
public class HomematicIPAccessPointHandler extends HomematicIPThingHandler<HomeControlAccessPoint> {

    private final Logger logger = LoggerFactory.getLogger(HomematicIPAccessPointHandler.class);

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_ACCESSPOINT);

    /**
     * Creates a new instance of this class for the {@link Thing}.
     *
     * @param thing the thing that should be handled, not null
     */
    public HomematicIPAccessPointHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command, HomeControlAccessPoint device) {
        logger.trace("Received command {} for channel {} and device {}", command, channelUID.getId(), device);
        if (command instanceof PercentType) {
            logger.trace("=> {} %", ((PercentType) command).floatValue());
            try {
                device.setSignalBrightness(((PercentType) command).floatValue() / 100);
            } catch (IOException e) {
                logger.error("Cannot handle command: {}", e.getMessage(), e);
            }
        } else if (command instanceof OnOffType) {
            logger.trace("=> {} (OnOffType)", ((OnOffType) command).toFullString());
        } else if (command instanceof IncreaseDecreaseType) {
            logger.trace("=> {} (IncreaseDecreaseType)", ((IncreaseDecreaseType) command).toFullString());
        }
    }

    @Override
    public HomematicIPHandlerType getThingHandlerType() {
        return HomematicIPHandlerType.DEVICE;
    }
}
