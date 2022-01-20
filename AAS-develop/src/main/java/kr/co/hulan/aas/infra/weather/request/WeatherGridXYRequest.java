package kr.co.hulan.aas.infra.weather.request;

import io.swagger.annotations.ApiModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WeatherGridXYRequest", description="날씨 단기실황/예보 요청")
public class WeatherGridXYRequest {
  private String serviceKey;
  private Integer pageNo = 1;
  private Integer numOfRows = 100;
  private String dataType = "JSON";
  /* 발표일자  yyyyMMdd */
  private String base_date;
  /* 발표시각  HHmm */
  private String base_time;
  private Integer nx;
  private Integer ny;

  public WeatherGridXYRequest(Integer nx, Integer ny ){
    this.nx = nx;
    this.ny = ny;
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.HOUR, -1);
    base_date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    base_time = new SimpleDateFormat("HHmm").format(cal.getTime());
  }

  public WeatherGridXYRequest(String base_date, String base_time, Integer nx, Integer ny ){
    this.base_date = base_date;
    this.base_time = base_time;
    this.nx = nx;
    this.ny = ny;
  }

  public String getUrlParams(){
    StringBuilder urlBuilder = new StringBuilder(); /*URL*/
    urlBuilder.append("serviceKey=").append(serviceKey); /*공공데이터포털에서 받은 인증키*/
    urlBuilder.append("&pageNo=").append(pageNo); /*페이지번호*/
    urlBuilder.append("&numOfRows=").append(numOfRows); /*한 페이지 결과 수*/
    urlBuilder.append("&dataType=").append(dataType); /*요청자료형식(XML/JSON)*/
    urlBuilder.append("&base_date=").append(base_date); /* yyyyMMdd */
    urlBuilder.append("&base_time=").append(base_time); /* HH00 */
    urlBuilder.append("&nx=").append(nx);
    urlBuilder.append("&ny=").append(ny);
    return urlBuilder.toString();
  }

}
