package kr.co.hulan.aas.infra.vworld;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;
import kr.co.hulan.aas.common.utils.JsonUtil;
import kr.co.hulan.aas.infra.vworld.response.Vworld;
import kr.co.hulan.aas.infra.weather.WeatherClient;
import kr.co.hulan.aas.infra.weather.response.KmaWetherResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
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
public class VworldClient {

  private static Logger log = LoggerFactory.getLogger(VworldClient.class);

  @Autowired
  private VworldProperties vworldProperties;

  @Autowired
  private CloseableHttpClient client;

  public Vworld convertAddressToGps(String address) {
    LocalTime start = LocalTime.now();
    HttpResponse response = null;
    try {
      StringBuilder urlBuilder = new StringBuilder(); /*URL*/
      urlBuilder.append(vworldProperties.getServiceUrl());
      //urlBuilder.append("?key=").append(URLEncoder.encode(vworldProperties.getServiceKey(), "UTF-8"));
      urlBuilder.append("?key=").append(vworldProperties.getServiceKey());
      urlBuilder.append("&service=address");
      urlBuilder.append("&request=getcoord");
      urlBuilder.append("&version=2.0");
      urlBuilder.append("&crs=epsg:4326");
      urlBuilder.append("&refine=true");
      urlBuilder.append("&simple=false");
      urlBuilder.append("&format=json");
      urlBuilder.append("&type=road");
      urlBuilder.append("&address=").append(URLEncoder.encode(address, "UTF-8"));
      HttpGet httpRequest = new HttpGet(urlBuilder.toString()); //POST 메소드 URL 새성

      httpRequest.setHeader("Accept", "application/json");
      httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

      response = client.execute(httpRequest);

      if( response != null && response.getStatusLine() != null ){
        if( response.getStatusLine().getStatusCode() == HttpStatus.OK.value()){
          HttpEntity entity = response.getEntity();
          String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
          log.debug(this.getClass().getSimpleName()+"|sendRequest|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase()+"|"+address+"|"+json);
          return (Vworld) JsonUtil.toObjectJson(json, Vworld.class);

        }
        else {
          log.warn(this.getClass().getSimpleName()+"|sendRequest|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase()+"|"+address+"|"+EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
        }
      }
      else {
        log.error(this.getClass().getSimpleName()+"|sendRequest|500|INVALID RESPONSE|"+address+"|INVALID RESPONSE");
      }
      return null;
    } catch (Exception e){
      log.error(this.getClass().getSimpleName()+"|sendRequest|500|INTERNAL SERVER ERROR OCCURED|"+address+"|"+e.getMessage(), e);
      return null;
    } finally{
      HttpClientUtils.closeQuietly(response);
      Duration duration = Duration.between(start, LocalTime.now());
      if( duration.getSeconds() > 1 ){
        log.warn( this.getClass().getSimpleName()+"|LongElapsedTime|" + duration.getSeconds());
      }
    }
  }


}
