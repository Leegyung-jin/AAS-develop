package kr.co.hulan.aas.config.security.oauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.oauth.CustomOauthException;
import kr.co.hulan.aas.config.security.oauth.service.SecurityUserService;
import kr.co.hulan.aas.config.database.PersistenceMyBatisConfiguration;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private final PersistenceMyBatisConfiguration dataSourceConfiguration;
  private final AuthenticationManager authenticationManager;
  private final CustomTokenEnhancer enhancer;
  private static final String JWT_KEY = "hulanJwt";
  @Autowired
  private SecurityUserService userService;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

  public AuthorizationServerConfiguration(
      PersistenceMyBatisConfiguration dataSourceConfiguration,
      AuthenticationManager authenticationManager,
      CustomTokenEnhancer enhancer) {
    this.dataSourceConfiguration = dataSourceConfiguration;
    this.authenticationManager = authenticationManager;
    this.enhancer = enhancer;
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(
        Arrays.asList(this.enhancer, jwtAccessTokenConverter())
    );
    endpoints
        .reuseRefreshTokens(false)
        .userDetailsService(userService)
        .authenticationManager(this.authenticationManager)
        .tokenGranter(tokenGranter(endpoints, this.authenticationManager))
        .exceptionTranslator(this.customWebResponseExceptionTranslator)
        .tokenEnhancer(tokenEnhancerChain)
//        .accessTokenConverter(jwtAccessTokenConverter()); // jwt 로 토큰 관리 (DB 저장 x)
        //.tokenStore(this.dataSourceConfiguration.tokenStore()); // db 로 토큰 관리 (DB 저장 O)
        .tokenStore(tokenStore) // db 로 토큰 관리 (DB 저장 O)
        ;
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setSigningKey(JWT_KEY);
    return jwtAccessTokenConverter;
  }

  private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints, final AuthenticationManager authenticationManager) {
    List<TokenGranter> granters = new ArrayList<TokenGranter>();
    granters.add(new CustomResourceOwnerPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), this.userService ));
    granters.addAll(Arrays.asList(endpoints.getTokenGranter()));
    return new CompositeTokenGranter(granters);
  }

  @Override
  public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
    clients.jdbc(this.dataSourceConfiguration.mybatisDataSource());
  }

  @Override
  public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
    oauthServer
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");
  }


}
