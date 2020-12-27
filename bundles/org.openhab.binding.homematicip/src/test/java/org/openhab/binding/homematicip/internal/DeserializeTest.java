package org.openhab.binding.homematicip.internal;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannel;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.gson.*;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.home.functional.FunctionalHome;
import org.openhab.binding.homematicip.internal.model.response.GetCurrentStateResponse;

import java.io.IOException;
import java.time.Instant;

/**
 * New class.
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class DeserializeTest {

    @Test
    public void testCurrentState() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Group.class, new GroupDeserializer());
        builder.registerTypeAdapter(Device.class, new DeviceDeserializer());
        builder.registerTypeAdapter(FunctionalHome.class, new FunctionalHomeDeserializer());
        builder.registerTypeAdapter(FunctionalChannel.class, new FunctionalChannelDeserializer());
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        var gson = builder.create();

        var jsonFile = new String(getClass().getClassLoader().getResourceAsStream("currentstate.json").readAllBytes());
        var state = gson.fromJson(jsonFile, GetCurrentStateResponse.class);
        System.out.println(state);
    }

}
