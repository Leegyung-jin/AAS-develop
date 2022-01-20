package kr.co.hulan.aas.infra.airkorea.station;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import kr.co.hulan.aas.config.properties.DataGoKrSvcProperties;
import kr.co.hulan.aas.infra.airkorea.AirkoreaProperties;
import kr.co.hulan.aas.infra.airkorea.environment.AirkoreaEnvironmentClient;
import kr.co.hulan.aas.infra.airkorea.station.dto.NearStationRequest;
import kr.co.hulan.aas.infra.airkorea.station.dto.NearStationResponseMessage;
import kr.co.hulan.aas.infra.airkorea.station.dto.TmCoordinateRequest;
import kr.co.hulan.aas.infra.airkorea.station.dto.TmCoordinateResponseMessage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AirkoreaStationClient {

  private static Logger logger = LoggerFactory.getLogger(AirkoreaEnvironmentClient.class);

  @Autowired
  private AirkoreaProperties airkoreaProperties;

  @Autowired
  private CloseableHttpClient client;

  private Gson gson = new GsonBuilder()
      .setDateFormat("yyyy-MM-dd HH:mm")
      .create();

  public NearStationResponseMessage getCtprvnRltmMesureDnsty(NearStationRequest request) {
    DataGoKrSvcProperties svcProperties = airkoreaProperties.getStationSvc();
    String callUrl = svcProperties.getServiceUrl()+"getNearbyMsrstnList";
    LocalTime start = LocalTime.now();
    HttpResponse response = null;
    try {
      StringBuilder urlBuilder = new StringBuilder(); /*URL*/
      urlBuilder.append(callUrl);
      urlBuilder.append("?serviceKey=").append(
          URLEncoder.encode(svcProperties.getServiceKey(), StandardCharsets.UTF_8.name())); /*공공데이터포털에서 받은 인증키*/
      urlBuilder.append("&returnType=JSON"); /*요청자료형식(XML/JSON)*/
      urlBuilder.append("&tmX=").append(request.getTmX().toString());
      urlBuilder.append("&tmY=").append(request.getTmY().toString());
      //urlBuilder.append("&ver=1.0");
      HttpGet httpRequest = new HttpGet(urlBuilder.toString()); //POST 메소드 URL 새성

      httpRequest.setHeader("Accept", "application/json");
      httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      response = client.execute(httpRequest);
      if( response != null && response.getStatusLine() != null ){
        if( response.getStatusLine().getStatusCode() == HttpStatus.OK.value()){
          HttpEntity entity = response.getEntity();
          String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
          logger.debug(this.getClass().getSimpleName()+"|sendRequest|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase()+"|\n"+json);
          return gson.fromJson(json, NearStationResponseMessage.class);
        }
        else {
          logger.warn(this.getClass().getSimpleName()+"|sendRequest|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase()+"||"+EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        }
      }
      else {
        logger.error(this.getClass().getSimpleName()+"|sendRequest|500|INVALID RESPONSE|INVALID RESPONSE");
      }
    } catch (Exception e){
      logger.error(this.getClass().getSimpleName()+"|sendRequest|500|INTERNAL SERVER ERROR OCCURED|"+e.getMessage(), e);
    } finally{
      HttpClientUtils.closeQuietly(response);
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        logger.warn( this.getClass().getSimpleName()+"|LongElapsedTime|" + duration.getSeconds());
      }
    }
    return null;
  }


  public TmCoordinateResponseMessage getTMStdrCrdnt(TmCoordinateRequest request) {
    DataGoKrSvcProperties svcProperties = airkoreaProperties.getStationSvc();
    String callUrl = svcProperties.getServiceUrl()+"getTMStdrCrdnt";
    LocalTime start = LocalTime.now();
    HttpResponse response = null;
    try {
      StringBuilder urlBuilder = new StringBuilder(); /*URL*/
      urlBuilder.append(callUrl);
      urlBuilder.append("?serviceKey=").append(
          URLEncoder.encode(svcProperties.getServiceKey(), StandardCharsets.UTF_8.name())); /*공공데이터포털에서 받은 인증키*/
      urlBuilder.append("&returnType=JSON"); /*요청자료형식(XML/JSON)*/
      urlBuilder.append("&numOfRows=").append(request.getNumOfRows());
      urlBuilder.append("&pageNo=").append(request.getPageNo());
      urlBuilder.append("&umdName=").append(URLEncoder.encode(request.getUmdName(), StandardCharsets.UTF_8.name()));
      urlBuilder.append("&ver=1.0");
      HttpGet httpRequest = new HttpGet(urlBuilder.toString()); //POST 메소드 URL 새성

      httpRequest.setHeader("Accept", "application/json");
      httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      response = client.execute(httpRequest);
      if( response != null && response.getStatusLine() != null ){
        if( response.getStatusLine().getStatusCode() == HttpStatus.OK.value()){
          HttpEntity entity = response.getEntity();
          String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
          logger.debug(this.getClass().getSimpleName()+"|sendRequest|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase()+"|\n"+json);
          return gson.fromJson(json, TmCoordinateResponseMessage.class);
        }
        else {
          logger.warn(this.getClass().getSimpleName()+"|sendRequest|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase()+"||"+EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        }
      }
      else {
        logger.error(this.getClass().getSimpleName()+"|sendRequest|500|INVALID RESPONSE|INVALID RESPONSE");
      }
    } catch (Exception e){
      logger.error(this.getClass().getSimpleName()+"|sendRequest|500|INTERNAL SERVER ERROR OCCURED|"+e.getMessage(), e);
    } finally{
      HttpClientUtils.closeQuietly(response);
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        logger.warn( this.getClass().getSimpleName()+"|LongElapsedTime|" + duration.getSeconds());
      }
    }
    return null;
  }

}
