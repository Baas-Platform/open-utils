package com.shinemo.baas.openapi.demo.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @date: 2021-01-21
 * @author: luchao
 * @email: luc@shinemo.com
 */
public class JsonUtils {

    private static Gson gson = new Gson();

    private static JsonParser parser = new JsonParser();

    public static String toJson(Object data) {
        return gson.toJson(data);
    }

    public static Gson getGson() {
        return gson;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        JsonArray jsonArray = parser.parse(json).getAsJsonArray();
        List<T> list = new ArrayList();
        for (JsonElement jsonElement : jsonArray) {
            list.add(gson.fromJson(jsonElement, clazz));
        }
        return list;
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .setDateFormat(DateFormat.LONG)
                .create();
        return gson.fromJson(json, typeOfT);
    }

    static class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
        }
    }
}
