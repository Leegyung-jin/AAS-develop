package kr.co.hulan.aas.config.web;

import com.google.common.collect.Lists;
import java.util.List;
import javax.servlet.ServletContext;
import kr.co.hulan.aas.config.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  @Autowired
  private SwaggerProperties swaggerProperties;

  @Bean
  public Docket codeApi(ServletContext servletContext) {
    return customDocket(
        "공통 코드",
        "공통 코드 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.code"
    );
  }

  @Bean
  public Docket fileApi(ServletContext servletContext) {
    return customDocket(
            "파일 관리",
            "파일 관리 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.file"
    );
  }

  @Bean
  public Docket memberApi(ServletContext servletContext) {
    return customDocket(
        "회원관리",
        "회원관리 메뉴 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.member"
    );
  }

  @Bean
  public Docket safetySituationApi(ServletContext servletContext) {
    return customDocket(
            "안전관리현황",
            "안전관리현황 메뉴 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.safetySituation"
    );
  }

  @Bean
  public Docket workplaceApi(ServletContext servletContext) {
    return customDocket(
            "현장관리",
            "현장관리 메뉴 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.workplace"
    );
  }

  /*
  @Bean
  public Docket sensorLogApi(ServletContext servletContext) {
    return customDocket(
            "안전세부현황",
            "안전세부현황 메뉴 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.sensorLog"
    );
  }
   */

  @Bean
  public Docket sensorTraceApi(ServletContext servletContext) {
    return customDocket(
            "위치관리",
            "위치관리 메뉴 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.sensorTrace"
    );
  }


  @Bean
  public Docket sensorMgrApi(ServletContext servletContext) {
    return customDocket(
            "안전센서관리",
            "안전센서관리 메뉴 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.sensorMgr"
    );
  }

  @Bean
  public Docket jobMgrApi(ServletContext servletContext) {
    return customDocket(
            "구인관리",
            "구인관리 메뉴 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.jobMgr"
    );
  }

  @Bean
  public Docket boardApi(ServletContext servletContext) {
    return customDocket(
            "게시판관리",
            "게시판관리 메뉴 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.board"
    );
  }

  @Bean
  public Docket mainApi(ServletContext servletContext) {
    return customDocket(
            "메인",
            "메인 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.main"
    );
  }

  @Bean
  public Docket myInfoApi(ServletContext servletContext) {
    return customDocket(
            "나의 정보",
            "나의 정보 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.myInfo"
    );
  }

  @Bean
  public Docket gpsApi(ServletContext servletContext) {
    return customDocket(
            "GPS 정보",
            "GPS 정보 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.gps"
    );
  }

  @Bean
  public Docket deviceApi(ServletContext servletContext) {
    return customDocket(
            "디바이스/장비 정보",
            "디바이스/장비 정보 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.device"
    );
  }

  @Bean
  public Docket entergateApi(ServletContext servletContext) {
    return customDocket(
        "출입게이트 연동 정보",
        "출입게이트 연동 정보 관리 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.entergate"
    );
  }

  @Bean
  public Docket blsApi(ServletContext servletContext) {
    return customDocket(
        "BLS 모니터링 정보",
        "BLS 모니터링 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.bls"
    );
  }

  @Bean
  public Docket monitorApi(ServletContext servletContext) {
    return customDocket(
            "일반 모니터링 정보",
            "일반 모니터링 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.monitor"
    );
  }

  @Bean
  public Docket trackerApi(ServletContext servletContext) {
    return customDocket(
        "트랙커 관리",
        "트랙커 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.tracker"
    );
  }

  @Bean
  public Docket pushApi(ServletContext servletContext) {
    return customDocket(
        "Push 관리",
        "Push 관리 메뉴 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.push"
    );
  }

  @Bean
  public Docket buildingApi(ServletContext servletContext) {
    return customDocket(
        "빌딩 관리",
        "빌딩 관리 메뉴 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.building"
    );
  }

  @Bean
  public Docket workSectionApi(ServletContext servletContext) {
    return customDocket(
            "공정 관리",
            "공정 관리 메뉴 Api 문서 입니다.",
            "kr.co.hulan.aas.mvc.api.worksection"
    );
  }

  @Bean
  public Docket gasApi(ServletContext servletContext) {
    return customDocket(
        "유해물질 관리",
        "유해물질 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.gas"
    );
  }

  @Bean
  public Docket accidentApi(ServletContext servletContext) {
    return customDocket(
        "사고/재해 이벤트 관리",
        "사고/재해 이벤트 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.accident"
    );
  }


  @Bean
  public Docket orderOfficeApi(ServletContext servletContext) {
    return customDocket(
        "발주사관리",
        "발주사관리 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.orderoffice"
    );
  }

  @Bean
  public Docket hiccApi(ServletContext servletContext) {
    return customDocket(
        "통합관제 API",
        "통합관제 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.hicc"
    );
  }

  @Bean
  public Docket componentApi(ServletContext servletContext) {
    return customDocket(
        "Component 관리 API",
        "Component 관리 API 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.component"
    );
  }

  /*
  @Bean
  public Docket sampleApi(ServletContext servletContext) {
    return customDocket(
        "샘플",
        "샘플 Api 문서 입니다.",
        "kr.co.hulan.aas.mvc.api.sample"
    );
  }
   */

  private Docket customDocket(String title, String msg, String dir) {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(new ApiInfoBuilder()
            .title(swaggerProperties.getName())
            .description(msg + "\n Authorize 에 'Bearer [access_token]' 형식으로 추가해서 사용하세요.")
            .build())
        .groupName(title)
        .useDefaultResponseMessages(false)
        .securityContexts(Lists.newArrayList(securityContext()))
        .securitySchemes(Lists.newArrayList(new ApiKey("JWT", AUTHORIZATION_HEADER, "header")))
        .select()
        .apis(RequestHandlerSelectors.basePackage(dir))
        .paths(PathSelectors.ant("/api/**"))
        .build();
  }

  // AUTHORIZATION_HEADER 버튼 추가
  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .build();
  }

  // AUTHORIZATION_HEADER 버튼 추가
  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
  }

}
