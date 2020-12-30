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
package org.openhab.binding.homematicip.internal.model;

import java.io.Reader;
import java.time.Instant;

import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannel;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.gson.*;
import org.openhab.binding.homematicip.internal.model.home.functional.FunctionalHome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Generic object
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-24
 */
public class HomematicIPObject {

    protected final static Gson gson;

    static {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(Group.class, new GroupDeserializer());
        builder.registerTypeAdapter(Device.class, new DeviceDeserializer());
        builder.registerTypeAdapter(FunctionalHome.class, new FunctionalHomeDeserializer());
        builder.registerTypeAdapter(FunctionalChannel.class, new FunctionalChannelDeserializer());
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        gson = builder.create();
    }

    public static HomematicIPObject fromJson(String json, Class<? extends HomematicIPObject> clazz) {
        var object = gson.fromJson(json, clazz);
        object.resolveMappings();
        return object;
    }

    public static HomematicIPObject fromJson(Reader json, Class<? extends HomematicIPObject> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public void resolveMappings() {
        // no-op
    }

}
