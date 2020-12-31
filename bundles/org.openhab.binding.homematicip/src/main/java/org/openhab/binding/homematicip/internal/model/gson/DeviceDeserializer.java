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
package org.openhab.binding.homematicip.internal.model.gson;

import java.lang.reflect.Type;

import org.openhab.binding.homematicip.internal.model.common.DeviceType;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.device.GenericDevice;

import com.google.gson.*;

/**
 * Deserialize device / polymorphism by type
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class DeviceDeserializer implements JsonDeserializer<Device<?>> {

    @Override
    public Device deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        String typeString = obj.get("type").getAsString();
        final var deviceType = DeviceType.valueOf(typeString);
        final var device = (Device<?>) (deviceType != null ? context.deserialize(jsonElement, deviceType.getClazz())
                : context.deserialize(jsonElement, GenericDevice.class));
        device.setType(typeString);
        return device;
    }
}
