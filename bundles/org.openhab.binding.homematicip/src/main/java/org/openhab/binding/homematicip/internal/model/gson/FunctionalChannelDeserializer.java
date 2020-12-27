/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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

import com.google.gson.*;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannel;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannelType;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.device.DeviceType;
import org.openhab.binding.homematicip.internal.model.group.GenericGroup;

import java.lang.reflect.Type;

/**
 * Deserialize device / polymorphism by type
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class FunctionalChannelDeserializer implements JsonDeserializer<FunctionalChannel> {

    @Override
    public FunctionalChannel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        String typeString = obj.get("functionalChannelType").getAsString();
        var functionalChannelType = FunctionalChannelType.valueOf(typeString);
        if (functionalChannelType != null) {
            return context.deserialize(jsonElement, functionalChannelType.getClazz());
        }
        return context.deserialize(jsonElement, GenericGroup.class);
    }
}
