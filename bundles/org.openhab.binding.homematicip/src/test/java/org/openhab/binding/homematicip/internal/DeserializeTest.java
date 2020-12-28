package org.openhab.binding.homematicip.internal;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.openhab.binding.homematicip.internal.model.HomematicIPObject;
import org.openhab.binding.homematicip.internal.model.gson.*;
import org.openhab.binding.homematicip.internal.model.response.GetCurrentStateResponse;

public class DeserializeTest {

    @Test
    public void testCurrentState() throws IOException {
        var jsonFile = new String(getClass().getClassLoader().getResourceAsStream("currentstate.json").readAllBytes());
        var state = (GetCurrentStateResponse) HomematicIPObject.fromJson(jsonFile, GetCurrentStateResponse.class);
        System.out.println(state);
    }
}
