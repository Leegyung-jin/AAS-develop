package kr.co.hulan.aas.infra.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WeatherResponse", description="날씨 요청 응답")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

  @ApiModelProperty(notes = "기준일자(yyyyMMdd)")
  private String baseDate;

  @ApiModelProperty(notes = "기준시간(HHmm)")
  private String baseTime;


  private RequestWeatherCommand request;
  private Map<String, WeatherItem> weatherItemMap = new HashMap();

  public void addWeatherItem(WeatherItem item){
    weatherItemMap.put(item.getType().getCode(), item);
  }

  public Date getBaseDateTime(){
    try{
      if(StringUtils.isNotBlank(baseDate) && StringUtils.isNotBlank(baseTime)){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date parsedDate = formatter.parse(baseDate+baseTime);
        return parsedDate;
      }
    }
    catch(Exception e){}
    return null;
  }

  @Override
  public String toString() {
    return "WeatherResponse{" +
        "baseDate='" + baseDate + '\'' +
        ", baseTime='" + baseTime + '\'' +
        ", request=" + request +
        ", weatherItemMap=" + weatherItemMap +
        '}';
  }
}
