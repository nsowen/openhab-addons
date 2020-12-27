package org.openhab.binding.homematicip.internal.model.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Deserialize to Instand epoch millis
 *
 * @author Nils Sowen (nils@sowen.de)
 * @since 2020-12-27
 */
public class InstantDeserializer implements JsonDeserializer<Instant> {
    @Override
    public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        Long timestamp = jsonElement.getAsLong();
        if (timestamp == null) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp);
    }
}
