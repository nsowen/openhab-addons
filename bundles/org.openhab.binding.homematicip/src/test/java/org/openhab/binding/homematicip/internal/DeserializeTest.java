package org.openhab.binding.homematicip.internal;

import com.google.gson.GsonBuilder;
import org.jose4j.http.Get;
import org.junit.jupiter.api.Test;
import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.channel.FunctionalChannel;
import org.openhab.binding.homematicip.internal.model.device.Device;
import org.openhab.binding.homematicip.internal.model.gson.*;
import org.openhab.binding.homematicip.internal.model.group.Group;
import org.openhab.binding.homematicip.internal.model.home.functional.FunctionalHome;
import org.openhab.binding.homematicip.internal.model.response.GetCurrentStateResponse;

import java.io.IOException;
import java.time.Instant;

public class DeserializeTest {

    @Test
    public void testCurrentState() throws IOException {
        var jsonFile = new String(getClass().getClassLoader().getResourceAsStream("currentstate.json").readAllBytes());
        var state = (GetCurrentStateResponse) HomematicIPObject.fromJson(jsonFile, GetCurrentStateResponse.class);
        System.out.println(state);
    }

}
