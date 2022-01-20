package kr.co.hulan.aas.config.security.oauth;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.transaction.Transactional;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.oauth.CustomOauthException;
import kr.co.hulan.aas.common.utils.IpUtil;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.config.security.oauth.service.SecurityUserService;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

public class CustomResourceOwnerPasswordTokenGranter extends AbstractTokenGranter {

  private static final String GRANT_TYPE = "password";

  private final AuthenticationManager authenticationManager;

  private SecurityUserService securityUserService;

  public CustomResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager,
      AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, SecurityUserService securityUserService) {
    this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE, securityUserService);
  }

  protected CustomResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType, SecurityUserService securityUserService) {
    super(tokenServices, clientDetailsService, requestFactory, grantType);
    this.authenticationManager = authenticationManager;
    this.securityUserService = securityUserService;
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

    Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
    String username = parameters.get("username");
    String password = parameters.get("password");
    // Protect from downstream leaks of password
    parameters.remove("password");

    SecurityUser userInfo = securityUserService.loadUserByUsername(username);
    try{
      Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
      ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

      try {
        userAuth = authenticationManager.authenticate(userAuth);
      }
      catch (AccountExpiredException | DisabledException ex ) {
        //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
        CustomOauthException coe = new CustomOauthException(BaseCode.ERR_OAUTH_DISABLED_USER.code(),ex.getMessage(), userAuth);
        throw coe;
      }
      catch ( LockedException ex ) {
        //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
        CustomOauthException coe = new CustomOauthException(BaseCode.ERR_OAUTH_ACCOUNT_LOCKED.code(),ex.getMessage(), userAuth);
        throw coe;
      }
      catch ( CredentialsExpiredException ex ) {
        CustomOauthException coe = new CustomOauthException(BaseCode.ERR_OAUTH_CREDENTIAL_EXPIRED, userAuth);
        coe.initCause(ex);
        throw coe;
      }
      catch ( BadCredentialsException e) {
        // If the username/password are wrong the spec says we should send 400/invalid grant
        CustomOauthException ex = new CustomOauthException(BaseCode.ERR_OAUTH_BAD_CREDENTIALS, userAuth);
        ex.initCause(e);
        throw ex;
      }
      catch (UsernameNotFoundException e) {
        // If the user is not found, report a generic error message
        CustomOauthException ex = new CustomOauthException( BaseCode.ERR_OAUTH_USER_NOT_FOUND, userAuth);
        ex.initCause(e);
        throw ex;
      }

      if (userAuth == null || !userAuth.isAuthenticated()) {
        CustomOauthException ex = new CustomOauthException( BaseCode.ERR_OAUTH_INVALID_GRANT.code(),"Could not authenticate user: " + username, userAuth);
        throw ex;
      }

      securityUserService.updateLoginSuccess(userInfo, IpUtil.getRequestRemoteAddr());

      OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
      return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
    catch(CustomOauthException e){
      if( e.getReturnCode() == BaseCode.ERR_OAUTH_BAD_CREDENTIALS.code() ){
        securityUserService.updateLoginFail(userInfo);
        e.addAdditionalInformation("fail_count", ""+userInfo.getAttemptLoginCount() );
      }
      throw e;
    }

  }

}
