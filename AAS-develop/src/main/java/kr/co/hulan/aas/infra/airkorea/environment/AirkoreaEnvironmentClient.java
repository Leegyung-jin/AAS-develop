package kr.co.hulan.aas.infra.airkorea.environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import kr.co.hulan.aas.config.properties.DataGoKrSvcProperties;
import kr.co.hulan.aas.infra.airkorea.AirkoreaProperties;
import kr.co.hulan.aas.infra.airkorea.environment.dto.AirEnvironmentBody;
import kr.co.hulan.aas.infra.airkorea.environment.dto.AirEnvironmentItem;
import kr.co.hulan.aas.infra.airkorea.environment.dto.AirEnvironmentRequest;
import kr.co.hulan.aas.infra.airkorea.environment.dto.AirEnvironmentResponseMessage;
import kr.co.hulan.aas.infra.airkorea.environment.service.AirkoreaEnvironmentService;
import kr.co.hulan.aas.mvc.service.weather.WeatherService;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AirkoreaEnvironmentClient {

  private static Logger logger = LoggerFactory.getLogger(AirkoreaEnvironmentClient.class);

  @Autowired
  private AirkoreaProperties airkoreaProperties;

  @Autowired
  private AirkoreaEnvironmentService airkoreaEnvironmentService;

  @Autowired
  private WeatherService weatherService;

  @Autowired
  private CloseableHttpClient client;

  private Gson gson = new GsonBuilder()
      .setDateFormat("yyyy-MM-dd HH:mm")
      .create();

  public AirEnvironmentResponseMessage getCtprvnRltmMesureDnsty(AirEnvironmentRequest request) {
    DataGoKrSvcProperties svcProperties = airkoreaProperties.getAirEnvironmentSvc();
    String callUrl = svcProperties.getServiceUrl()+"getCtprvnRltmMesureDnsty";
    LocalTime start = LocalTime.now();
    HttpResponse response = null;
    try {
      StringBuilder urlBuilder = new StringBuilder(); /*URL*/
      urlBuilder.append(callUrl);
      urlBuilder.append("?serviceKey=").append(URLEncoder.encode(svcProperties.getServiceKey(), StandardCharsets.UTF_8.name())); /*공공데이터포털에서 받은 인증키*/
      urlBuilder.append("&returnType=JSON"); /*요청자료형식(XML/JSON)*/
      urlBuilder.append("&numOfRows=").append(request.getNumOfRows()); /*한 페이지 결과 수*/
      urlBuilder.append("&pageNo=").append(request.getPageNo()); /*페이지번호*/
      urlBuilder.append("&sidoName=").append(URLEncoder.encode(request.getSidoName(), StandardCharsets.UTF_8.name()));
      urlBuilder.append("&ver=1.3");
      HttpGet httpRequest = new HttpGet(urlBuilder.toString()); //POST 메소드 URL 새성

      httpRequest.setHeader("Accept", "application/json");
      httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      response = client.execute(httpRequest);
      if( response != null && response.getStatusLine() != null ){
        if( response.getStatusLine().getStatusCode() == HttpStatus.OK.value()){
          HttpEntity entity = response.getEntity();
          String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
          logger.debug(this.getClass().getSimpleName()+"|sendRequest|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase()+"|\n"+json);
          return gson.fromJson( json, AirEnvironmentResponseMessage.class);
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

  @Scheduled(cron = "0 11 * * * ?")
  public void refreshAirEnvironment(){
    saveAirEnvironment();
    updateSidoAirEnvironment();
  }

  public void saveAirEnvironment(){
    logger.info(this.getClass().getSimpleName()+"|refreshAirEnvironment|Start|");
    try{
      AirEnvironmentRequest request = AirEnvironmentRequest.builder()
          .pageNo(1)
          .numOfRows(100)
          .sidoName("전국")
          .build();
      int totalCount = 0;
      int refreshCount = 0;

      while( true ){
        AirEnvironmentResponseMessage result = getCtprvnRltmMesureDnsty(request);
        if( result == null || !result.isSuccess()  ){
          break;
        }
        AirEnvironmentBody resultBody = result.getResponseBody();
        List<AirEnvironmentItem> items = resultBody.getItems();
        if( items.size() == 0 ){
          break;
        }
        airkoreaEnvironmentService.saveAirEnvironmemt(items);
        totalCount = resultBody.getTotalCount();
        refreshCount += items.size();
        if(!resultBody.isContinue()){
          break;
        }
        request.setPageNo( request.getPageNo() + 1);
        Thread.sleep(1000);
      }
      logger.info(this.getClass().getSimpleName()+"|refreshAirEnvironment|End|"+refreshCount+"/"+totalCount);
    }
    catch(Exception e){
      logger.info(this.getClass().getSimpleName()+"|refreshAirEnvironment|Error|"+e.getMessage(), e);
    }
  }

  public void updateSidoAirEnvironment(){
    weatherService.updateSidoAirEnvironment();
  }


}
