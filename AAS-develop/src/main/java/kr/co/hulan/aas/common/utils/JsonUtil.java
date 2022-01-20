package kr.co.hulan.aas.common.utils;
import com.google.common.base.CaseFormat;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.stream.Collectors;

public class JsonUtil {
    public static String toStringJson(Object obj) throws Exception {
        return toStringJson(obj, true);
    }

    public static String toStringJson(Object obj, boolean indent) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.configure(SerializationFeature.INDENT_OUTPUT, indent);

        return jsonMapper.writeValueAsString(obj);
    }

    public static Object toObjectJson(String json, Class<?> valueType) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.readValue(json, valueType);
    }


    public static Object toObjectMap(Object obj, Class<?> valueType) {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.convertValue(obj, valueType);
    }


    public static String removeAttr(String json, String exculdeKey) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Map<String, Object> map = jsonMapper.readValue(
                json, new TypeReference<Map<String, Object>>() {});
        map.remove(exculdeKey);

        return toStringJson(map);
    }

    public static Map<String, Object> toStringMap(String json) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        Map<String, Object> map = jsonMapper.readValue(
                json, new TypeReference<Map<String, Object>>() {});

        return map;
    }

    public static String appendObjAttr(String json, String key, Object o) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Map<String, Object> map = jsonMapper.readValue(
                json, new TypeReference<Map<String, Object>>() {});
        map.put(key, o);

        return toStringJson(map);
    }

    public static String appendJsonAttr(String json, String key, String s) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Map<String, Object> map = jsonMapper.readValue(
                json, new TypeReference<Map<String, Object>>() {});

        map.put(key, jsonMapper.readValue(s, new TypeReference<Map<String, Object>>() {}));

        return toStringJson(map);
    }

}
