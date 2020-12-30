package org.openhab.binding.homematicip.internal.discovery;

import static org.openhab.binding.homematicip.internal.HomematicIPBindingConstants.*;

import java.util.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.homematicip.internal.handler.HomematicIPAccessPointHandler;
import org.openhab.binding.homematicip.internal.handler.HomematicIPBridgeHandler;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.core.config.discovery.AbstractDiscoveryService;
import org.openhab.core.config.discovery.DiscoveryResult;
import org.openhab.core.config.discovery.DiscoveryResultBuilder;
import org.openhab.core.config.discovery.DiscoveryService;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Discovers new things connected to the HomematicIP bridge
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-28
 */
@NonNullByDefault
@Component(service = DiscoveryService.class, configurationPid = "discovery.homematicip")
public class HomematicIPDiscoveryService extends AbstractDiscoveryService
        implements DiscoveryService, ThingHandlerService {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPE_UIDS = HomematicIPAccessPointHandler.SUPPORTED_THING_TYPES_UIDS;

    private final Logger logger = LoggerFactory.getLogger(HomematicIPDiscoveryService.class);
    private @Nullable HomematicIPBridgeHandler bridgeHandler;
    private @Nullable ThingUID bridgeUID;

    public HomematicIPDiscoveryService() throws IllegalArgumentException {
        super(SUPPORTED_THING_TYPE_UIDS, 300);
        logger.debug("Created HomematicIPDiscoveryService");
    }

    @Override
    public void setThingHandler(@Nullable ThingHandler handler) {
        if (handler instanceof HomematicIPBridgeHandler) {
            logger.debug("Received bridge: {}", handler);
            bridgeHandler = (HomematicIPBridgeHandler) handler;
            bridgeUID = handler.getThing().getUID();
        } else {
            logger.warn("No bridge, instead this: {}", handler);
        }
    }

    @Override
    public Set<ThingTypeUID> getSupportedThingTypes() {
        return SUPPORTED_THING_TYPE_UIDS;
    }

    @Override
    protected void startBackgroundDiscovery() {
        logger.debug("Start background discovery");
    }

    @Override
    protected void stopBackgroundDiscovery() {
        logger.debug("Stop background discovery");
    }

    @Override
    public void startScan() {
        logger.debug("Starting scan: {}", bridgeHandler);
        final var handler = bridgeHandler;
        if (handler != null) {
            handler.getDevices().forEach(this::addDeviceDiscovery);
            handler.getGroups().forEach(this::addGroupDiscovery);
        }
    }

    private void addGroupDiscovery(Group group) {
        logger.debug("Adding discovery: {}", group);
    }

    public void addDeviceDiscovery(Device device) {
        logger.debug("Adding discovery: {}", device);
        ThingUID thingUID = getThingUID(device);
        ThingTypeUID thingTypeUID = getThingTypeUID(device);
        String modelId = device.getModelType();
        if (thingUID != null && thingTypeUID != null) {
            Map<String, Object> properties = new HashMap<>();
            if (modelId != null) {
                properties.put(Thing.PROPERTY_MODEL_ID, modelId);
            }
            String uniqueID = device.getId();
            if (uniqueID != null) {
                properties.put(UNIQUE_ID, uniqueID);
            }
            String oem = device.getOem();
            if (oem != null) {
                properties.put(OEM, oem);
            }
            String firmwareVersion = device.getFirmwareVersion();
            if (firmwareVersion != null) {
                properties.put(FIRMWARE_VERSION, firmwareVersion);
            }
            DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID).withThingType(thingTypeUID)
                    .withProperties(properties).withBridge(bridgeUID).withRepresentationProperty(UNIQUE_ID)
                    .withLabel(device.getLabel()).build();
            logger.debug("discovered supported device of type '{}' and model '{}' with id {}", device.getDeviceType(),
                    modelId, device.getId());
            thingDiscovered(discoveryResult);
        } else {
            logger.debug("discovered unsupported device of type '{}' and model '{}' with id {}", device.getDeviceType(),
                    modelId, device.getId());
        }
    }

    private @Nullable ThingUID getThingUID(Device device) {
        ThingUID localBridgeUID = bridgeUID;
        if (localBridgeUID != null) {
            ThingTypeUID thingTypeUID = getThingTypeUID(device);
            if (thingTypeUID != null && getSupportedThingTypes().contains(thingTypeUID)) {
                logger.debug("Created thingUID: {}, id={}, localBridge={}", thingTypeUID, device.getId(),
                        localBridgeUID);
                return new ThingUID(thingTypeUID, localBridgeUID, device.getId());
            } else {
                logger.warn("Not supported: {}, supported are: {}", thingTypeUID, getSupportedThingTypes());
            }
        } else {
            logger.warn("No localBridgeUID");
        }
        return null;
    }

    private @Nullable ThingTypeUID getThingTypeUID(Device device) {
        String thingTypeId = null;
        logger.debug("device: " + device.getDeviceType());
        if (device.getDeviceType().equals("HOME_CONTROL_ACCESS_POINT")) {
            thingTypeId = "accesspoint"; // TODO: create thing type ids for each device
        }
        return thingTypeId != null ? new ThingTypeUID(BINDING_ID, thingTypeId) : null;
    }

    @Override
    public @Nullable ThingHandler getThingHandler() {
        return bridgeHandler;
    }

    @Override
    public void activate() {
        logger.debug("activating discovery");
        final var handler = bridgeHandler;
        if (handler != null) {
            handler.registerDiscoveryListener(this);
        }
    }

    @Override
    public void deactivate() {
        logger.debug("deactivating discovery");
        removeOlderResults(new Date().getTime(), bridgeUID);
        final var handler = bridgeHandler;
        if (handler != null) {
            handler.unregisterDiscoveryListener();
        }
    }
}
