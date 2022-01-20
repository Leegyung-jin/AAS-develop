package kr.co.hulan.aas.config.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@FrameworkEndpoint
public class CustomRevokeTokenEndpoint {

  @Autowired
  private ConsumerTokenServices consumerTokenServices;

  @DeleteMapping("/oauth/token")
  @ResponseBody
  public boolean revokeToken(HttpServletRequest request) {
    String token = request.getHeader("TOKEN");
    if (token != null) {
      return consumerTokenServices.revokeToken(token);
    }
    return false;
  }
}
