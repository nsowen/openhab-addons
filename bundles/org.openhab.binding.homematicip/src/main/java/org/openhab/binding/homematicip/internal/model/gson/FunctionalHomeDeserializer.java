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

import org.openhab.binding.homematicip.internal.model.common.FunctionalHomeType;
import org.openhab.binding.homematicip.internal.model.home.functional.FunctionalHome;
import org.openhab.binding.homematicip.internal.model.home.functional.GenericFunctionalHome;

import com.google.gson.*;

/**
 * Deserialize functional homes by solution
 *
 * @author Nils Sowen - Initial contribution
 * @since 2020-12-27
 */
public class FunctionalHomeDeserializer implements JsonDeserializer<FunctionalHome> {

    @Override
    public FunctionalHome deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        String typeString = obj.get("solution").getAsString();
        var objType = FunctionalHomeType.valueOf(typeString);
        if (objType != null) {
            return context.deserialize(jsonElement, objType.getClazz());
        }
        return context.deserialize(jsonElement, GenericFunctionalHome.class);
    }
}
