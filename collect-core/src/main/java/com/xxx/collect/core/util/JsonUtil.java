package com.xxx.collect.core.util;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;


public class JsonUtil {

  private static final ObjectMapper jsonMapper = new ObjectMapper();

  static {
    //忽略空属性
    jsonMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
  }

  public static <T> T readStringAsValue(String json, Class<T> valueType) {
    try {
      return jsonMapper.readValue(json, valueType);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String writeValueAsString(Object obj) {
    try {
      return jsonMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

  public static JsonNode readTree(String content) {
    try {
      return JSON_MAPPER.readTree(content);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

}
