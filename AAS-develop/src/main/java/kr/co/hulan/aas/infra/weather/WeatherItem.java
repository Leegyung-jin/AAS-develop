package kr.co.hulan.aas.infra.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import kr.co.hulan.aas.common.code.WeatherItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WeatherItemType", description="날씨 정보 항목")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherItem {

  private WeatherItemType type;
  private String value;

}
