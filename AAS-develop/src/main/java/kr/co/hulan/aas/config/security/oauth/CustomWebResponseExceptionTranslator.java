package kr.co.hulan.aas.config.security.oauth;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.oauth.CustomOauthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomWebResponseExceptionTranslator implements
    WebResponseExceptionTranslator<OAuth2Exception> {

  public ResponseEntity<OAuth2Exception> translate(Exception exception) throws Exception {
    if (exception instanceof CustomOauthException) {
      CustomOauthException customOauthException = (CustomOauthException) exception;
      return ResponseEntity
          .status(customOauthException.getHttpErrorCode())
          .body(customOauthException);
    }
    else if (exception instanceof OAuth2Exception) {
      OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
      return ResponseEntity
          .status(oAuth2Exception.getHttpErrorCode())
          .body(new CustomOauthException(BaseCode.ERR_OAUTH_INVALID_GRANT.code(), oAuth2Exception.getLocalizedMessage(), null));
    }
    else if (exception instanceof UsernameNotFoundException) {
      UsernameNotFoundException usernameNotFoundException = (UsernameNotFoundException) exception;
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(new CustomOauthException(BaseCode.ERR_OAUTH_USER_NOT_FOUND, null));
    }
    else {
      log.warn("Not translated Exception. "+exception.getClass().getSimpleName());
      throw exception;
    }
  }
}
