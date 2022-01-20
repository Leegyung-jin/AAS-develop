package kr.co.hulan.aas.infra.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuthProxyService {

  private Logger logger = LoggerFactory.getLogger(OAuthProxyService.class);

  @Value(value = "${oauth.url}")
  String localOauthUrl;

  @Autowired
  private RestTemplate restTemplate;

  public ResponseEntity<String> localLogin(String basicAuth, String username, String password) {
    try{
      String requestUrl = localOauthUrl + "oauth/token";

      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", basicAuth);
      headers.add("Accept", "application/json");
      headers.add("Content-Type", "application/x-www-form-urlencoded");

      StringBuilder sb = new StringBuilder();
      sb.append("grant_type=password").append("&");
      sb.append("username=").append(URLEncoder.encode(username, "UTF-8")).append("&");
      sb.append("password=").append(URLEncoder.encode(password, "UTF-8"));
      String body = sb.toString();

      HttpEntity entity = new HttpEntity( body, headers);

      return restTemplate.exchange(requestUrl, HttpMethod.POST, entity, String.class);
    } catch (HttpStatusCodeException exception) {
      logger.error(this.getClass().getSimpleName()+"|login|"+exception.getStatusCode().value()+"|"+exception.getResponseBodyAsString());
      return new ResponseEntity(exception.getResponseBodyAsString(), exception.getStatusCode());
    } catch (Exception e){
      logger.error(this.getClass().getSimpleName()+"|login|500|INTERNAL SERVER ERROR OCCURED", e);
      throw new CommonException(BaseCode.ERR_ETC_EXCEPTION.code(), "사용자 인증 연동 실패하였습니다.");
    }
  }


}
