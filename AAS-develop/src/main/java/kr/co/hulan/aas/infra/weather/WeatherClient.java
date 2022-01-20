package kr.co.hulan.aas.infra.weather;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import kr.co.hulan.aas.infra.weather.request.WeatherForecastRequest;
import kr.co.hulan.aas.infra.weather.request.WeatherGridXYRequest;
import kr.co.hulan.aas.infra.weather.response.KmaResponse;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherLandFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherMidLandFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherMidTaItem;
import kr.co.hulan.aas.infra.weather.response.KmaWeatherVilageFcstItem;
import kr.co.hulan.aas.infra.weather.response.KmaWetherBodyItemResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;


@Component
public class WeatherClient {

  private static Logger log = LoggerFactory.getLogger(WeatherClient.class);

  @Autowired
  private WeatherProperties weatherProperties;

  @Autowired
  private CloseableHttpClient client;

  @Autowired
  private RestTemplate restTemplate;

  @PostConstruct
  public void postConstruct(){

  }

  public KmaResponse<KmaWetherBodyItemResponse> findWeatherUltraNcst(WeatherGridXYRequest request){
    LocalTime start = LocalTime.now();
    try {
      request.setServiceKey(weatherProperties.getServiceKey());

      HttpHeaders headers = new HttpHeaders();
      headers.add("Accept", "application/json");
      headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity( headers);

      String callUrl = weatherProperties.getServiceUltraSrtNcstUrl()+"?"+request.getUrlParams();

      ParameterizedTypeReference<KmaResponse<KmaWetherBodyItemResponse>>  parameterizedTypeReference =
          new ParameterizedTypeReference<KmaResponse<KmaWetherBodyItemResponse>>(){};

      ResponseEntity<KmaResponse<KmaWetherBodyItemResponse>> response = restTemplate.exchange(callUrl, HttpMethod.GET, entity, parameterizedTypeReference);
      log.debug(this.getClass().getSimpleName()+"|findWeatherUltraNcst|"+response.getStatusCode().value()+"|"+response.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams());
      return response.getBody();

    } catch (HttpStatusCodeException exception) {
      log.error(this.getClass().getSimpleName()+"|findWeatherUltraNcst|"+exception.getStatusCode().value()+"|"+exception.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch(UnknownContentTypeException exception){
      log.error(this.getClass().getSimpleName()+"|findWeatherUltraNcst|500|UnknownContentType|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch (Exception e){
      log.error(this.getClass().getSimpleName()+"|findWeatherUltraNcst|500|INTERNAL SERVER ERROR OCCURED|"+request.getUrlParams()+"|"+e.getMessage(), e);
      return null;
    } finally{
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        log.warn( this.getClass().getSimpleName()+"|findWeatherUltraNcst|LongElapsedTime|" + duration.getSeconds());
      }
    }
  }

  public KmaResponse<KmaWeatherVilageFcstItem> findWeatherUltraFcst(WeatherGridXYRequest request){
    LocalTime start = LocalTime.now();
    try {
      request.setServiceKey(weatherProperties.getServiceKey());

      HttpHeaders headers = new HttpHeaders();
      headers.add("Accept", "application/json");
      headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity( headers);

      String callUrl = weatherProperties.getServiceUltraSrtFcstUrl()+"?"+request.getUrlParams();

      ParameterizedTypeReference<KmaResponse<KmaWeatherVilageFcstItem>>  parameterizedTypeReference =
          new ParameterizedTypeReference<KmaResponse<KmaWeatherVilageFcstItem>>(){};

      ResponseEntity<KmaResponse<KmaWeatherVilageFcstItem>> response = restTemplate.exchange(callUrl, HttpMethod.GET, entity, parameterizedTypeReference);
      log.debug(this.getClass().getSimpleName()+"|findWeatherUltraFcst|"+response.getStatusCode().value()+"|"+response.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams());
      return response.getBody();

    } catch (HttpStatusCodeException exception) {
      log.error(this.getClass().getSimpleName()+"|findWeatherUltraFcst|"+exception.getStatusCode().value()+"|"+exception.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch(UnknownContentTypeException exception){
      log.error(this.getClass().getSimpleName()+"|findWeatherUltraFcst|500|UnknownContentType|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch (Exception e){
      log.error(this.getClass().getSimpleName()+"|findWeatherUltraFcst|500|INTERNAL SERVER ERROR OCCURED|"+request.getUrlParams()+"|"+e.getMessage(), e);
      return null;
    } finally{
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        log.warn( this.getClass().getSimpleName()+"|findWeatherUltraFcst|LongElapsedTime|" + duration.getSeconds());
      }
    }
  }


  public KmaResponse<KmaWeatherVilageFcstItem> findWeatherVilageFcst(WeatherGridXYRequest request){
    LocalTime start = LocalTime.now();
    try {
      request.setServiceKey(weatherProperties.getServiceKey());

      HttpHeaders headers = new HttpHeaders();
      headers.add("Accept", "application/json");
      headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity( headers);

      String callUrl = weatherProperties.getServiceVilageFcstUrl()+"?"+request.getUrlParams();

      ParameterizedTypeReference<KmaResponse<KmaWeatherVilageFcstItem>>  parameterizedTypeReference =
          new ParameterizedTypeReference<KmaResponse<KmaWeatherVilageFcstItem>>(){};

      ResponseEntity<KmaResponse<KmaWeatherVilageFcstItem>> response = restTemplate.exchange(callUrl, HttpMethod.GET, entity, parameterizedTypeReference);
      log.debug(this.getClass().getSimpleName()+"|findWeatherVilageFcst|"+response.getStatusCode().value()+"|"+response.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams());
      return response.getBody();

    } catch (HttpStatusCodeException exception) {
      log.error(this.getClass().getSimpleName()+"|findWeatherVilageFcst|"+exception.getStatusCode().value()+"|"+exception.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch(UnknownContentTypeException exception){
      log.error(this.getClass().getSimpleName()+"|findWeatherVilageFcst|500|UnknownContentType|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch (Exception e){
      log.error(this.getClass().getSimpleName()+"|findWeatherVilageFcst|500|INTERNAL SERVER ERROR OCCURED|"+request.getUrlParams()+"|"+e.getMessage(), e);
      return null;
    } finally{
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        log.warn( this.getClass().getSimpleName()+"|findWeatherVilageFcst|LongElapsedTime|" + duration.getSeconds());
      }
    }
  }

  public KmaResponse<KmaWeatherMidLandFcstItem> findWeatherMidLandFcst(WeatherForecastRequest request) {
    LocalTime start = LocalTime.now();
    try{
      request.setServiceKey(weatherProperties.getServiceKey());

      HttpHeaders headers = new HttpHeaders();
      headers.add("Accept", "application/json");
      headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity(headers);

      String callUrl = weatherProperties.getServiceMidLandFcstUrl()+"?"+request.getUrlParams();

      ParameterizedTypeReference<KmaResponse<KmaWeatherMidLandFcstItem>>  parameterizedTypeReference =
          new ParameterizedTypeReference<KmaResponse<KmaWeatherMidLandFcstItem>>(){};

      ResponseEntity<KmaResponse<KmaWeatherMidLandFcstItem>> response = restTemplate.exchange(callUrl, HttpMethod.GET, entity, parameterizedTypeReference);
      log.debug(this.getClass().getSimpleName()+"|findWeatherMidLandFcst|"+response.getStatusCode().value()+"|"+response.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams());
      return response.getBody();
    } catch (HttpStatusCodeException exception) {
      log.error(this.getClass().getSimpleName()+"|findWeatherMidLandFcst|"+exception.getStatusCode().value()+"|"+exception.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch(UnknownContentTypeException exception){
      log.error(this.getClass().getSimpleName()+"|findWeatherMidLandFcst|500|UnknownContentType|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch (Exception e){
      log.error(this.getClass().getSimpleName()+"|findWeatherMidLandFcst|500|INTERNAL SERVER ERROR OCCURED|"+request.getUrlParams()+"|"+e.getMessage(), e);
      return null;
    } finally{
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        log.warn( this.getClass().getSimpleName()+"|findWeatherMidLandFcst|LongElapsedTime|" + duration.getSeconds());
      }
    }
  }


  public KmaResponse<KmaWeatherMidTaItem> findWeatherMidTa(WeatherForecastRequest request) {
    LocalTime start = LocalTime.now();
    try{
      request.setServiceKey(weatherProperties.getServiceKey());

      HttpHeaders headers = new HttpHeaders();
      headers.add("Accept", "application/json");
      headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity(headers);

      String callUrl = weatherProperties.getServiceMidTaUrl()+"?"+request.getUrlParams();

      ParameterizedTypeReference<KmaResponse<KmaWeatherMidTaItem>>  parameterizedTypeReference =
          new ParameterizedTypeReference<KmaResponse<KmaWeatherMidTaItem>>(){};

      ResponseEntity<KmaResponse<KmaWeatherMidTaItem>> response = restTemplate.exchange(callUrl, HttpMethod.GET, entity, parameterizedTypeReference);
      log.debug(this.getClass().getSimpleName()+"|findWeatherMidTa|"+response.getStatusCode().value()+"|"+response.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams());
      return response.getBody();
    } catch (HttpStatusCodeException exception) {
      log.error(this.getClass().getSimpleName()+"|findWeatherMidTa|"+exception.getStatusCode().value()+"|"+exception.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch(UnknownContentTypeException exception){
      log.error(this.getClass().getSimpleName()+"|findWeatherMidTa|500|UnknownContentType|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch (Exception e){
      log.error(this.getClass().getSimpleName()+"|findWeatherMidTa|500|INTERNAL SERVER ERROR OCCURED|"+request.getUrlParams()+"|"+e.getMessage(), e);
      return null;
    } finally{
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        log.warn( this.getClass().getSimpleName()+"|findWeatherMidTa|LongElapsedTime|" + duration.getSeconds());
      }
    }
  }

  public KmaResponse<KmaWeatherLandFcstItem> findWeatherLandFcst(WeatherForecastRequest request) {
    LocalTime start = LocalTime.now();
    try{
      request.setServiceKey(weatherProperties.getServiceKey());

      HttpHeaders headers = new HttpHeaders();
      headers.add("Accept", "application/json");
      headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity(headers);

      String callUrl = weatherProperties.getServiceLandFcstUrl()+"?"+request.getUrlParams();

      ParameterizedTypeReference<KmaResponse<KmaWeatherLandFcstItem>>  parameterizedTypeReference =
          new ParameterizedTypeReference<KmaResponse<KmaWeatherLandFcstItem>>(){};

      ResponseEntity<KmaResponse<KmaWeatherLandFcstItem>> response = restTemplate.exchange(callUrl, HttpMethod.GET, entity, parameterizedTypeReference);
      log.debug(this.getClass().getSimpleName()+"|findWeatherLandFcst|"+response.getStatusCode().value()+"|"+response.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams());
      return response.getBody();
    } catch (HttpStatusCodeException exception) {
      log.error(this.getClass().getSimpleName()+"|findWeatherLandFcst|"+exception.getStatusCode().value()+"|"+exception.getStatusCode().getReasonPhrase()+"|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch(UnknownContentTypeException exception){
      log.error(this.getClass().getSimpleName()+"|findWeatherLandFcst|500|UnknownContentType|"+request.getUrlParams()+"|"+exception.getResponseBodyAsString());
      return null;
    } catch (Exception e){
      log.error(this.getClass().getSimpleName()+"|findWeatherLandFcst|500|INTERNAL SERVER ERROR OCCURED|"+request.getUrlParams()+"|"+e.getMessage(), e);
      return null;
    } finally{
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        log.warn( this.getClass().getSimpleName()+"|findWeatherLandFcst|LongElapsedTime|" + duration.getSeconds());
      }
    }
  }

}
