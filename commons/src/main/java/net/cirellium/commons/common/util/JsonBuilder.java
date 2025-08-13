package net.cirellium.commons.common.util;

import com.google.gson.JsonObject;

public record JsonBuilder(JsonObject json) implements SimpleProvider<JsonObject> {
    
    public <V> JsonBuilder addProperty(String key, V value) {
        switch (value.getClass().getSimpleName()) {
            case "String" -> json.addProperty(key, (String) value);
            case "Boolean" -> json.addProperty(key, (Boolean) value);
            case "Character" -> json.addProperty(key, (Character) value);
            case "Number" -> json.addProperty(key, (Number) value);
            default -> json.addProperty(key, value.toString());
        }
        return this;
    }

    @Override
    public JsonObject provide() {
        return json;
    }
}