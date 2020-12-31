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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openhab.binding.homematicip.internal.discovery.HomematicIPDiscoveryService;
import org.openhab.binding.homematicip.internal.handler.HomematicIPBridgeHandler;
import org.openhab.binding.homematicip.internal.model.common.DeviceType;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.device.HomeControlAccessPoint;
import org.openhab.core.config.core.Configuration;
import org.openhab.core.config.discovery.DiscoveryListener;
import org.openhab.core.config.discovery.DiscoveryResult;
import org.openhab.core.config.discovery.DiscoveryResultFlag;
import org.openhab.core.config.discovery.DiscoveryService;
import org.openhab.core.thing.*;
import org.openhab.core.thing.binding.builder.ThingStatusInfoBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.openhab.core.thing.Thing.PROPERTY_SERIAL_NUMBER;

/**
 * Tests for {@link org.openhab.binding.homematicip.internal.discovery.HomematicIPDiscoveryService}.
 *
 * @author Nils Sowen - Initial contribution
 */
public class HomematicIPDiscoveryServiceOSGiTest extends AbstractHomematicIPOSGiTestParent {

    protected HomematicIPHandlerFactory hueThingHandlerFactory;
    protected DiscoveryListener discoveryListener;
    protected ThingRegistry thingRegistry;
    protected Bridge bridge;
    protected HomematicIPBridgeHandler bridgeHandler;
    protected HomematicIPDiscoveryService discoveryService;

    protected final ThingTypeUID BRIDGE_THING_TYPE_UID = new ThingTypeUID("hue", "bridge");
    protected final ThingUID BRIDGE_THING_UID = new ThingUID(BRIDGE_THING_TYPE_UID, "testBridge");

    @BeforeEach
    public void setUp() {
        registerVolatileStorageService();

        thingRegistry = getService(ThingRegistry.class, ThingRegistry.class);
        assertThat(thingRegistry, is(notNullValue()));

        Configuration configuration = new Configuration();
        configuration.put(PROPERTY_SERIAL_NUMBER, "testSerialNumber");

        bridge = (Bridge) thingRegistry.createThingOfType(BRIDGE_THING_TYPE_UID, BRIDGE_THING_UID, null, "Bridge",
                configuration);

        assertThat(bridge, is(notNullValue()));
        thingRegistry.add(bridge);

        bridgeHandler = getThingHandler(bridge, HomematicIPBridgeHandler.class);
        assertThat(bridgeHandler, is(notNullValue()));

        discoveryService = getService(DiscoveryService.class, HomematicIPDiscoveryService.class);
        assertThat(discoveryService, is(notNullValue()));
    }

    @AfterEach
    public void cleanUp() {
        thingRegistry.remove(BRIDGE_THING_UID);
        waitForAssert(() -> {
            assertNull(getService(DiscoveryService.class, HomematicIPDiscoveryService.class));
        });
    }

    private void registerDiscoveryListener(DiscoveryListener discoveryListener) {
        unregisterCurrentDiscoveryListener();
        this.discoveryListener = discoveryListener;
        discoveryService.addDiscoveryListener(this.discoveryListener);
    }

    private void unregisterCurrentDiscoveryListener() {
        if (this.discoveryListener != null) {
            discoveryService.removeDiscoveryListener(this.discoveryListener);
        }
    }

    @Test
    public void deviceRegistration() {

        Device device = new HomeControlAccessPoint();
        device.setId("4711");
        device.setType(DeviceType.HOME_CONTROL_ACCESS_POINT.name());
        device.setOem("ACME");
        device.setFirmwareVersion("1.0");

        AtomicReference<DiscoveryResult> resultWrapper = new AtomicReference<>();

        registerDiscoveryListener(new DiscoveryListener() {
            @Override
            public void thingDiscovered(DiscoveryService source, DiscoveryResult result) {
                resultWrapper.set(result);
            }

            @Override
            public void thingRemoved(DiscoveryService source, ThingUID thingUID) {
            }

            @Override
            public Collection<ThingUID> removeOlderResults(DiscoveryService source, long timestamp,
                    Collection<ThingTypeUID> thingTypeUIDs, ThingUID bridgeUID) {
                return null;
            }
        });

        discoveryService.addDeviceDiscovery(device);
        waitForAssert(() -> {
            assertTrue(resultWrapper.get() != null);
        });

        final DiscoveryResult result = resultWrapper.get();
        assertThat(result.getFlag(), is(DiscoveryResultFlag.NEW));
        assertThat(result.getThingUID().toString(), is("hue:0210:testBridge:" + device.getId()));
        assertThat(result.getThingTypeUID(), is("accesspoint"));
        assertThat(result.getBridgeUID(), is(bridge.getUID()));
        assertThat(result.getProperties().get(HomematicIPBindingConstants.UNIQUE_ID), is(device.getId()));
    }

    /*
    @Test
    public void startSearchIsCalled() {
        final AtomicBoolean searchHasBeenTriggered = new AtomicBoolean(false);

        MockedHttpClient mockedHttpClient = new MockedHttpClient() {

            @Override
            public Result put(String address, String body) throws IOException {
                return new Result("", 200);
            }

            @Override
            public Result get(String address) throws IOException {
                if (address.endsWith("testUserName")) {
                    String body = "{\"lights\":{}}";
                    return new Result(body, 200);
                } else if (address.endsWith("lights") || address.endsWith("sensors") || address.endsWith("groups")) {
                    String body = "{}";
                    return new Result(body, 200);
                } else if (address.endsWith("testUserName/config")) {
                    String body = "{ \"apiversion\": \"1.26.0\"}";
                    return new Result(body, 200);
                } else {
                    return new Result("", 404);
                }
            }

            @Override
            public Result post(String address, String body) throws IOException {
                if (address.endsWith("lights")) {
                    String bodyReturn = "{\"success\": {\"/lights\": \"Searching for new devices\"}}";
                    searchHasBeenTriggered.set(true);
                    return new Result(bodyReturn, 200);
                } else {
                    return new Result("", 404);
                }
            }
        };

        installHttpClientMock(bridgeHandler, mockedHttpClient);

        ThingStatusInfo online = ThingStatusInfoBuilder.create(ThingStatus.ONLINE, ThingStatusDetail.NONE).build();
        waitForAssert(() -> {
            assertThat(bridge.getStatusInfo(), is(online));
        });

        discoveryService.startScan();
        waitForAssert(() -> {
            assertTrue(searchHasBeenTriggered.get());
        });
    }

    private void installHttpClientMock(HueBridgeHandler hueBridgeHandler, MockedHttpClient mockedHttpClient) {
        waitForAssert(() -> {
            try {
                // mock HttpClient
                final Field hueBridgeField = HueBridgeHandler.class.getDeclaredField("hueBridge");
                hueBridgeField.setAccessible(true);
                final Object hueBridgeValue = hueBridgeField.get(hueBridgeHandler);
                assertThat(hueBridgeValue, is(notNullValue()));

                final Field httpClientField = HueBridge.class.getDeclaredField("http");
                httpClientField.setAccessible(true);
                httpClientField.set(hueBridgeValue, mockedHttpClient);

                final Field usernameField = HueBridge.class.getDeclaredField("username");
                usernameField.setAccessible(true);
                usernameField.set(hueBridgeValue, hueBridgeHandler.getThing().getConfiguration().get(USER_NAME));
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                fail("Reflection usage error");
            }
        });
        hueBridgeHandler.initialize();
    }
     */
}
