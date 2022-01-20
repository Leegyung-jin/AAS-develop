package kr.co.hulan.aas.common.exception.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.hulan.aas.common.code.BaseCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends InvalidGrantException {

  @JsonProperty("return_code")
  private Integer returnCode;

  @JsonProperty("return_message")
  private String returnMessage;

  @JsonIgnoreProperties
  private Authentication userAuth;

  public CustomOauthException(int returnCode, String returnMessage, Authentication userAuth ) {
    super(returnMessage);
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.userAuth = userAuth;
  }

  public CustomOauthException(BaseCode baseCode, Authentication userAuth) {
    this(baseCode.code(), baseCode.message(), userAuth);
  }

  public Integer getReturnCode() {
    return returnCode;
  }

  public String getReturnMessage() {
    return returnMessage;
  }

  @JsonIgnore
  public Authentication getUserAuth() {
    return userAuth;
  }
}
