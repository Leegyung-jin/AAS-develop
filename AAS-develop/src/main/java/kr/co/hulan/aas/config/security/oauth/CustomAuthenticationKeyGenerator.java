package kr.co.hulan.aas.config.security.oauth;

import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class CustomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

  private static final String CLIENT_ID = "client_id";

  private static final String SCOPE = "scope";

  private static final String USERNAME = "username";

  private static final String UUID_KEY = "uuid";

  public String extractKey(OAuth2Authentication authentication) {
    Map<String, String> values = new LinkedHashMap<>();
    OAuth2Request authorizationRequest = authentication.getOAuth2Request();
    if (!authentication.isClientOnly()) {
      values.put(USERNAME, authentication.getName());
    }
    values.put(CLIENT_ID, authorizationRequest.getClientId());
    if (authorizationRequest.getScope() != null) {
      values.put(SCOPE, OAuth2Utils.formatParameterList(authorizationRequest.getScope()));
    }
    Map<String, Serializable> extentions = authorizationRequest.getExtensions();
    String uuid = UUID.randomUUID().toString();
    if (extentions == null) {
      extentions = new HashMap<>(1);
      extentions.put(UUID_KEY, uuid);
    } else {
      extentions.computeIfAbsent(UUID_KEY, key -> uuid);
    }
    values.put(UUID_KEY, uuid);

    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
    }

    try {
      byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
      return String.format("%032x", new BigInteger(1, bytes));
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
    }
  }
}
