/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.homematicip.internal;

import static org.openhab.binding.homematicip.internal.HomematicIPBindingConstants.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.homematicip.internal.discovery.HomematicIPDiscoveryService;
import org.openhab.binding.homematicip.internal.handler.HomematicIPBridgeHandler;
import org.openhab.binding.homematicip.internal.handler.HomematicIPThingHandler;
import org.openhab.core.config.core.Configuration;
import org.openhab.core.config.discovery.DiscoveryService;
import org.openhab.core.i18n.TimeZoneProvider;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.io.net.http.WebSocketFactory;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link HomematicIPHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Nils Sowen - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.homematicip", service = ThingHandlerFactory.class)
public class HomematicIPHandlerFactory extends BaseThingHandlerFactory {

    private final Logger logger = LoggerFactory.getLogger(HomematicIPDiscoveryService.class);
    private final HttpClientFactory httpClientFactory;
    private final WebSocketFactory webSocketFactory;
    private final TimeZoneProvider timeZoneProvider;
    private final Map<ThingUID, ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();

    @Activate
    public HomematicIPHandlerFactory(final @Reference HttpClientFactory httpClientFactory,
            final @Reference WebSocketFactory webSocketFactory, final @Reference TimeZoneProvider timeZoneProvider) {
        this.httpClientFactory = httpClientFactory;
        this.webSocketFactory = webSocketFactory;
        this.timeZoneProvider = timeZoneProvider;
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return HomematicIPBridgeHandler.SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID)
                || HomematicIPThingHandler.SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    public @Nullable Thing createThing(ThingTypeUID thingTypeUID, Configuration configuration,
            @Nullable ThingUID thingUID, @Nullable ThingUID bridgeUID) {
        if (HomematicIPBridgeHandler.SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID)) {
            logger.debug("Creating bridge thing: {}", thingUID);
            return super.createThing(thingTypeUID, configuration, thingUID, null);
        } else if (HomematicIPThingHandler.SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID)) {
            logger.debug("Creating homematicip thing: {}", thingUID);
            return super.createThing(thingTypeUID, configuration, thingUID, bridgeUID);
        }
        throw new IllegalArgumentException(
                "The thing type " + thingTypeUID + " is not supported by the homematicip binding.");
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        if (THING_TYPE_BRIDGE.equals(thing.getThingTypeUID())) {
            var bridge = new HomematicIPBridgeHandler((Bridge) thing, httpClientFactory, webSocketFactory);
            registerDiscoveryService(bridge);
            return bridge;
        } else {
            return new HomematicIPThingHandler(thing);
        }
    }

    @Override
    protected synchronized void removeHandler(ThingHandler thingHandler) {
        if (thingHandler instanceof HomematicIPBridgeHandler) {
            ServiceRegistration<?> serviceReg = this.discoveryServiceRegs.remove(thingHandler.getThing().getUID());
            if (serviceReg != null) {
                serviceReg.unregister();
            }
        }
    }

    private synchronized void registerDiscoveryService(HomematicIPBridgeHandler bridgeHandler) {
        logger.debug("Registering homematicip discovery service...");
        var discoveryService = new HomematicIPDiscoveryService();
        discoveryService.setThingHandler(bridgeHandler);
        discoveryServiceRegs.put(bridgeHandler.getThing().getUID(),
                bundleContext.registerService(DiscoveryService.class.getName(), discoveryService, new Hashtable<>()));
        bridgeHandler.registerDiscoveryListener(discoveryService);
    }
}
