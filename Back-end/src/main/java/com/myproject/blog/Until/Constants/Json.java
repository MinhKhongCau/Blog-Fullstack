package com.myproject.blog.Until.Constants;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    private static ObjectMapper objectMapper;
    
    public static String toJson(Object object)  {
        String json = null;
        try {
            objectMapper = new ObjectMapper();

        json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    return json;
    }

    public static String toJson(List<Object> listObject) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(listObject);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    public static List<Object> toListJson(String json) {
        List<Object> objects = null;
        try {
            objects = objectMapper.readValue(json, new TypeReference<List<Object>>(){});
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return objects;
    }
}
