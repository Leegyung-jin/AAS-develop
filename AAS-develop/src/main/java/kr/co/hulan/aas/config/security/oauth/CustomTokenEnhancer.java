package kr.co.hulan.aas.config.security.oauth;

import java.util.HashMap;
import java.util.Map;
import kr.co.hulan.aas.config.security.oauth.model.SecurityMemberResponse;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.config.security.oauth.service.DetailSecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

  @Autowired
  private DetailSecurityUserService detailSecurityUserService;

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
    Map<String, Object> additionalInfo = new HashMap<>();
    additionalInfo.put("member_info", getUser(securityUser.getUsername()));
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }

  private SecurityMemberResponse getUser(String userId) {
    SecurityMemberResponse user = null;
    if (userId != null) {
      user = detailSecurityUserService.getDetailSecurityUser(userId);
    }
    return user;
  }
}
