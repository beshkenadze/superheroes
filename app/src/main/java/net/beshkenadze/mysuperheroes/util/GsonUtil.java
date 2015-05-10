package net.beshkenadze.mysuperheroes.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.net.URI;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 26.02.2015.
 */
public class GsonUtil {
    // GSON is thread safe
    private static Gson sGson;

    public static Gson getInstance () {
        if (sGson == null) {
            synchronized (GsonUtil.class) {
                if (sGson == null) {
                    sGson = new GsonBuilder()
                            .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
                            .registerTypeAdapter(URI.class, new URISerializer())
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();
                }
            }
        }
        return sGson;
    }

    public static class DateTimeSerializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
        @Override
        public JsonElement serialize (DateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public DateTime deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return DateTime.parse(json.getAsString());
        }
    }

    private static class URISerializer implements JsonSerializer<URI>, JsonDeserializer<URI> {
        @Override
        public URI deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return URI.create(json.getAsString());
        }

        @Override
        public JsonElement serialize (URI src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }
}
