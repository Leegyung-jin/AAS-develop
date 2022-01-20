package kr.co.hulan.aas.common.utils;

import com.google.gson.JsonObject;

import java.util.Set;

public class UriFormatUtils {

  public String addToUri(JsonObject object) {
    StringBuilder builder = new StringBuilder();
    Set<String> keys = object.keySet();
    keys.forEach(o -> {
      if (builder.length() > 0) {
        builder.append("&");
      }
      builder.append(o).append("=").append(object.get(o).getAsString());
    });
    return builder.toString();
  }
}
