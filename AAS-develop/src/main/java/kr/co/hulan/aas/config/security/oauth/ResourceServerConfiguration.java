package kr.co.hulan.aas.config.security.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableWebSecurity
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
            "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-ui.html",
            "/swagger-resources/configuration/security").permitAll() // swagger
        .antMatchers("/common/login/**").permitAll()
        .antMatchers("/api/board/notice/create", "/api/board/notice/update/**", "/api/board/notice/delete/**", "/api/board/notice/deleteMultiple").hasAnyRole("4", "5", "6", "7", "10")
        .antMatchers("/api/equipment/create", "/api/equipment/update/**", "/api/equipment/delete/**", "/api/equipment/deleteMultiple").hasAnyRole("4", "5", "6", "7", "10")
        .antMatchers("/api/monitor/4.1/workplace/*/hazard/*/*").hasAnyRole("4", "5", "6", "7", "10")
        .antMatchers("/api/monitor/4.1/workplace/*/watchout/falling/*/confirm").hasAnyRole("4", "5", "6", "7", "10")
        .antMatchers("/api/workplace/analyticInfo").hasAnyRole("4", "5", "6", "7", "10")
        .antMatchers("/api/**").authenticated();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources
        //.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        .accessDeniedHandler(new CustomAccessDeniedHandler());
  }
}
