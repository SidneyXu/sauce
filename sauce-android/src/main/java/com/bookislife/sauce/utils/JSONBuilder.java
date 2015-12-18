package com.bookislife.sauce.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by SidneyXu on 2015/12/18.
 */
public abstract class JSONBuilder {

    public static ObjectBuilder newJSONObject() {
        ObjectBuilder builder = new ObjectBuilder();
        builder.jsonObject = new JSONObject();
        return builder;
    }

    public static ArrayBuilder newJSONArray() {
        ArrayBuilder builder = new ArrayBuilder();
        builder.jsonArray = new JSONArray();
        return builder;
    }

    public static ObjectBuilder newJSONObject(String s) {
        ObjectBuilder builder = new ObjectBuilder();
        try {
            builder.jsonObject = new JSONObject(s);
        } catch (JSONException e) {
            builder.jsonObject = new JSONObject();
        }
        return builder;
    }

    public static ArrayBuilder newJSONArray(String s) {
        ArrayBuilder builder = new ArrayBuilder();
        try {
            builder.jsonArray = new JSONArray(s);
        } catch (JSONException e) {
            builder.jsonArray = new JSONArray();
        }
        return builder;
    }

    public abstract String asString();


    public static class ObjectBuilder extends JSONBuilder {
        private JSONObject jsonObject;

        public ObjectBuilder putIfNotNull(String name, Object value) {
            try {
                if (value == null) return this;
                jsonObject.put(name, value);
                return this;
            } catch (JSONException e) {
                return this;
            }
        }

        public ObjectBuilder putIfNotEmpty(String name, Map<?, ?> value) {
            try {
                if (value == null || value.isEmpty()) return this;
                jsonObject.put(name, value);
                return this;
            } catch (JSONException e) {
                return this;
            }
        }

        public ObjectBuilder putIfNotEmpty(String name, JSONObject value) {
            try {
                if (value == null || value.length() > 0) return this;
                jsonObject.put(name, value);
                return this;
            } catch (JSONException e) {
                return this;
            }
        }

        public ObjectBuilder put(String name, Object value) {
            try {
                jsonObject.put(name, value);
                return this;
            } catch (JSONException e) {
                return this;
            }
        }

        public ObjectBuilder put(Map<String, String> map) {
            if (null == map) return this;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                try {
                    jsonObject.put(entry.getKey(), entry.getValue());
                } catch (JSONException ignored) {
                }
            }
            return this;
        }

        @Override
        public String asString() {
            return jsonObject.toString();
        }
    }

    public static class ArrayBuilder extends JSONBuilder {
        private JSONArray jsonArray;

        @Override
        public String asString() {
            return jsonArray.toString();
        }

        public ArrayBuilder putIfNotNull(Object value) {
            if (value == null) return this;
            jsonArray.put(value);
            return this;
        }

        public ArrayBuilder put(Object value) {
            jsonArray.put(value);
            return this;
        }
    }

}
