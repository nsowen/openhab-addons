package org.openhab.binding.homematicip.internal.handler;

import static org.openhab.binding.homematicip.internal.HomematicIPBindingConstants.UNIQUE_ID;
import static org.openhab.core.thing.Thing.*;
import static org.openhab.core.thing.Thing.PROPERTY_VENDOR;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.homematicip.internal.HomematicIPConnection;
import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.core.thing.*;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle for things
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-30
 */
public abstract class HomematicIPThingHandler<T extends HomematicIPObject> extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(HomematicIPThingHandler.class);

    private @Nullable HomematicIPConnection connection;
    private @Nullable ScheduledFuture<?> scheduledFuture;
    private boolean propertiesInitializedSuccessfully = false;

    /**
     * Creates a new instance of this class for the {@link Thing}.
     *
     * @param thing the thing that should be handled, not null
     */
    public HomematicIPThingHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        logger.debug("Initializing handler.");
        Bridge bridge = getBridge();
        initializeThing((bridge == null) ? null : bridge.getStatus());
    }

    @Override
    public void bridgeStatusChanged(ThingStatusInfo bridgeStatusInfo) {
        logger.debug("bridgeStatusChanged {}", bridgeStatusInfo);
        initializeThing(bridgeStatusInfo.getStatus());
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.trace("Received command {} for channelUID {}", command, channelUID.getId());
        var bridgeHandler = getHandler();
        if (bridgeHandler == null) {
            logger.warn("Homematic IP handler not found. Cannot handle command without bridge.");
            return;
        }
        var item = getUniqueId().map(this::getThingDetails).orElse(Optional.empty());
        if (!item.isPresent()) {
            logger.debug("item not known on bridge. Cannot handle command.");
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "@text/offline.conf-error-wrong-unique-id");
            return;
        }
        handleCommand(channelUID, command, item.get());
    }

    protected abstract void handleCommand(ChannelUID channelUID, Command command, T item);

    public abstract HomematicIPHandlerType getThingHandlerType();

    private void initializeThing(@Nullable ThingStatus bridgeStatus) {
        logger.debug("initializeThing thing {} bridge status {}", getThing().getUID(), bridgeStatus);
        var uniqueId = getUniqueId();
        if (uniqueId.isPresent()) {
            // note: this call implicitly registers our handler as a listener on the bridge
            if (getHandler() != null && getHandler().isReadyForUse()) {
                if (bridgeStatus == ThingStatus.ONLINE) {
                    var object = getThingDetails(uniqueId.get());
                    if (object.isPresent()) {
                        initializeProperties(object.get());
                        updateStatus(ThingStatus.ONLINE);
                    } else {
                        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.GONE);
                    }
                } else {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
                }
            } else {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_UNINITIALIZED);
            }
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "@text/offline.conf-error-no-unique-id");
        }
    }

    private synchronized void initializeProperties(@Nullable HomematicIPObject object) {
        if (object instanceof Device) {
            if (!propertiesInitializedSuccessfully && object != null) {
                var device = (Device) object;
                Map<String, String> properties = editProperties();
                String softwareVersion = device.getFirmwareVersion();
                if (softwareVersion != null) {
                    properties.put(PROPERTY_FIRMWARE_VERSION, softwareVersion);
                }
                String modelId = device.getModelType();
                properties.put(PROPERTY_MODEL_ID, modelId);
                String vendor = device.getOem();
                if (vendor != null) {
                    properties.put(PROPERTY_VENDOR, vendor);
                }
                String uniqueID = device.getId();
                if (uniqueID != null) {
                    properties.put(UNIQUE_ID, uniqueID);
                }
                updateProperties(properties);
                propertiesInitializedSuccessfully = true;
            }
        } else if (object instanceof Group) {
            // todo group
        }
    }

    public Optional<String> getUniqueId() {
        final String configUniqueId = (String) getConfig().get(UNIQUE_ID);
        logger.debug("config: {}", getConfig().toString());
        return Optional.ofNullable(configUniqueId);
    }

    public Optional<T> getThingDetails(String uniqueId) {
        if (getThingHandlerType() == HomematicIPHandlerType.DEVICE) {
            return (Optional<T>) getHandler().getDeviceById(uniqueId);
        }
        if (getThingHandlerType() == HomematicIPHandlerType.GROUP) {
            return (Optional<T>) getHandler().getGroupById(uniqueId);
        }
        throw new IllegalArgumentException("Unknown type: " + getThingHandlerType());
    }

    protected HomematicIPBridgeHandler getHandler() {
        return (HomematicIPBridgeHandler) getBridge().getHandler();
    }
}
