package kr.co.hulan.aas.infra.weather.request;

import io.swagger.annotations.ApiModel;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WeatherForecastRequest", description="날씨 예보정보 요청")
public class WeatherForecastRequest {

  private String serviceKey;
  private Integer pageNo = 1;
  private Integer numOfRows = 100;
  private String dataType = "JSON";
  /* 예보구역코드 */
  private String regId;
  /* 발표시각 - 일 2회(06:00,18:00)회 생성 되며 발표시각을 입력  YYYYMMDD0600(1800)  */
  private String tmFc;

  public WeatherForecastRequest(String regId, String tmFc ){
    this.regId = regId;
    this.tmFc = tmFc;
  }

  public WeatherForecastRequest(String serviceKey, String regId, String tmFc ){
    this.serviceKey = serviceKey;
    this.regId = regId;
    this.tmFc = tmFc;
  }

  public String getUrlParams(){
    StringBuilder urlBuilder = new StringBuilder(); /*URL*/
    urlBuilder.append("serviceKey=").append(serviceKey); /*공공데이터포털에서 받은 인증키*/
    urlBuilder.append("&pageNo=").append(pageNo); /*페이지번호*/
    urlBuilder.append("&numOfRows=").append(numOfRows); /*한 페이지 결과 수*/
    urlBuilder.append("&dataType=").append(dataType); /*요청자료형식(XML/JSON)*/
    urlBuilder.append("&regId=").append(regId);
    if(StringUtils.isNotBlank(tmFc)){
      urlBuilder.append("&tmFc=").append(tmFc);
    }
    return urlBuilder.toString();
  }

  public Map<String, Object> getParamsMap(){
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("serviceKey", serviceKey);
    params.put("pageNo", pageNo);
    params.put("numOfRows", numOfRows);
    params.put("dataType", dataType);
    params.put("regId", regId);
    params.put("tmFc", tmFc);
    return params;
  }

  public String getHttpUrlTemplate(String url){

    return UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("serviceKey", serviceKey)
        .queryParam("pageNo", pageNo)
        .queryParam("numOfRows", numOfRows)
        .queryParam("dataType", dataType)
        .queryParam("regId", regId)
        .queryParam("tmFc", tmFc)
        //.encode()
        .toUriString();
  }


}
