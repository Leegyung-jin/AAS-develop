package kr.co.hulan.aas.mvc.api.hicc.vo.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.openapi.vo.DailyWeatherForecastInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceWeatherForecastVo", description="현장 일주일 날씨 예보 및 현황 정보")
public class HiccWorkplaceWeatherForecastVo {

  @ApiModelProperty(notes = "현장 현재 날씨 정보")
  private HiccWeatherInfoVo currentWeather;

  @ApiModelProperty(notes = "현장 일주일 날씨 예보 정보")
  private List<DailyWeatherForecastInfo> forecastWeather;

}
