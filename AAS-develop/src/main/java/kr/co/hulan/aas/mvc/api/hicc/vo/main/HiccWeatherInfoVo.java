package kr.co.hulan.aas.mvc.api.hicc.vo.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.PmGrade;
import kr.co.hulan.aas.common.code.WeatherForecastForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWeatherInfoVo", description="날씨 정보")
public class HiccWeatherInfoVo {

  @ApiModelProperty(notes = "측정일")
  private String baseDate;
  @ApiModelProperty(notes = "측정시")
  private String baseTime;

  @ApiModelProperty(notes = "습도(%)")
  private String humidity;
  @ApiModelProperty(notes = "풍향(0~360)")
  private String windDirection;
  @ApiModelProperty(notes = "풍속(m/s)")
  private String windSpeed;
  @ApiModelProperty(notes = "강수 형태 코드")
  private String precipitationForm;
  @ApiModelProperty(notes = "강수 형태명")
  private String precipitationFormName;
  @ApiModelProperty(notes = "강수량")
  private String precipitation;
  @ApiModelProperty(notes = "하늘형태 코드. 1:맑음, 3:구름많음, 4:흐림")
  private String skyForm;
  @ApiModelProperty(notes = "하늘 형태명")
  private String skyFormName;
  @ApiModelProperty(notes = "강수확률")
  private String rainfall;
  @ApiModelProperty(notes = "온도")
  private String temperature;
  @ApiModelProperty(notes = "미세먼지(PM1.0) 지수")
  private String pm10Value;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 지수")
  private String pm25Value;

  @ApiModelProperty(notes = "오후 날씨형태 아이콘 코드")
  public String getWeatherIconCode(){
    WeatherForecastForm form = WeatherForecastForm.get(skyForm, precipitationForm);
    return form != null ? form.getWeatherIconCode() : "";
  }

  @ApiModelProperty(notes = "날씨형태명")
  public String getWfFormName(){
    WeatherForecastForm form = WeatherForecastForm.get(skyForm, precipitationForm);
    return form != null ? form.getName() : "";
  }

}
