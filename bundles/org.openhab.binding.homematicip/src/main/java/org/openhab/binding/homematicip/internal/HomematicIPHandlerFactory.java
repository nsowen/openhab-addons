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

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.config.core.Configuration;
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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link HomematicIPHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Nils Sowen - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.homematicip", service = ThingHandlerFactory.class)
public class HomematicIPHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_BRIDGE);
    private final HttpClientFactory httpClientFactory;
    private final WebSocketFactory webSocketFactory;
    private final TimeZoneProvider timeZoneProvider;

    @Activate
    public HomematicIPHandlerFactory(final @Reference HttpClientFactory httpClientFactory,
            final @Reference WebSocketFactory webSocketFactory, final @Reference TimeZoneProvider timeZoneProvider) {
        this.httpClientFactory = httpClientFactory;
        this.webSocketFactory = webSocketFactory;
        this.timeZoneProvider = timeZoneProvider;
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    public @Nullable Thing createThing(ThingTypeUID thingTypeUID, Configuration configuration,
            @Nullable ThingUID thingUID, @Nullable ThingUID bridgeUID) {
        if (THING_TYPE_BRIDGE.equals(thingTypeUID)) {
            return super.createThing(thingTypeUID, configuration, thingUID, null);
        }
        throw new IllegalArgumentException(
                "The thing type " + thingTypeUID + " is not supported by the homematicip binding.");
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        if (THING_TYPE_BRIDGE.equals(thing.getThingTypeUID())) {
            return new HomematicIPBridgeHandler((Bridge) thing, httpClientFactory, webSocketFactory);
        } else {
            return new HomematicIPThingHandler(thing);
        }
    }
}
