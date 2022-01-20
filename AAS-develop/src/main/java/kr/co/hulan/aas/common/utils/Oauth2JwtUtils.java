package kr.co.hulan.aas.common.utils;

import java.util.Base64;

public class Oauth2JwtUtils {

  public String decodePLD(String token) {
    String result = null;
    String[] jwt = token.split("[.]");
    if (jwt.length == 3) {
      Base64.Decoder decoder = Base64.getDecoder();
      result = new String(decoder.decode(jwt[1]));
    }
    return result;
  }
}
