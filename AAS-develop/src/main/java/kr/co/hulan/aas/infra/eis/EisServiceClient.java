package kr.co.hulan.aas.infra.eis;

import java.time.Duration;
import java.time.LocalTime;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.infra.weather.request.WeatherGridXYRequest;
import kr.co.hulan.aas.infra.weather.response.KmaResponse;
import kr.co.hulan.aas.infra.weather.response.KmaWetherBodyItemResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

@Component
public class EisServiceClient {

  private Logger logger = LoggerFactory.getLogger(EisServiceClient.class);

  @Autowired
  private EisProperties properties;

  @Autowired
  private RestTemplate restTemplate;

  public boolean syncChannel(long nvrNo){
    String authenticationToken = AuthenticationHelper.getToken();
    if( StringUtils.isBlank(authenticationToken)){
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    LocalTime start = LocalTime.now();
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer "+authenticationToken);
      headers.add("Accept", "application/json");
      headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity( headers);

      String callUrl = properties.getChannelSyncUrl(nvrNo);

      ResponseEntity<DefaultHttpRes> response = restTemplate.exchange(callUrl, HttpMethod.POST, entity, DefaultHttpRes.class);

      if( response.getStatusCode() == HttpStatus.OK ){
        DefaultHttpRes res = response.getBody();
        if( res != null ){
          logger.debug(this.getClass().getSimpleName()+"|syncChannel|"+response.getStatusCode().value()+"("+res.getReturnCode().intValue()+")|"+response.getStatusCode().getReasonPhrase()+"|"+nvrNo);
          if( res.getReturnCode().intValue() == BaseCode.SUCCESS.code()  ){
            return true;
          }
        }
        else {
          logger.debug(this.getClass().getSimpleName()+"|syncChannel|"+response.getStatusCode().value()+"( )|"+response.getStatusCode().getReasonPhrase()+"|"+nvrNo);
        }
      }
      else {
        logger.debug(this.getClass().getSimpleName()+"|syncChannel|"+response.getStatusCode().value()+"|"+response.getStatusCode().getReasonPhrase()+"|"+nvrNo);
      }
    } catch (HttpStatusCodeException exception) {
      logger.error(this.getClass().getSimpleName()+"|syncChannel|"+exception.getStatusCode().value()+"|"+exception.getStatusCode().getReasonPhrase()+"|"+nvrNo+"|"+exception.getResponseBodyAsString());
    } catch(UnknownContentTypeException exception){
      logger.error(this.getClass().getSimpleName()+"|syncChannel|500|UnknownContentType|"+nvrNo+"|"+exception.getResponseBodyAsString());
    } catch (Exception e){
      logger.error(this.getClass().getSimpleName()+"|syncChannel|500|INTERNAL SERVER ERROR OCCURED|"+nvrNo+"|"+e.getMessage(), e);
    } finally{
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        logger.warn( this.getClass().getSimpleName()+"|syncChannel|LongElapsedTime|" + duration.getSeconds());
      }
    }
    return false;
  }
}
