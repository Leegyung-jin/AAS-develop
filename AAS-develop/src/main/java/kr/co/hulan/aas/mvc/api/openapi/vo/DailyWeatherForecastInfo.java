package kr.co.hulan.aas.mvc.api.openapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.WeatherForecastForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="DailyWeatherForecastInfo", description="일 날씨 정보")
public class DailyWeatherForecastInfo {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "예보일")
  private String forecastDay;
  @ApiModelProperty(notes = "오전 강수확률(%)")
  private String amRainfall;
  @ApiModelProperty(notes = "오전 날씨형태")
  private String amWfFormName;
  @ApiModelProperty(notes = "오전 하늘형태")
  private String amSkyForm;
  @ApiModelProperty(notes = "오전 강수형태")
  private String amPrecipitationForm;
  @ApiModelProperty(notes = "오후 강수확률(%)")
  private String pmRainfall;
  @ApiModelProperty(notes = "오후 날씨형태")
  private String pmWfFormName;
  @ApiModelProperty(notes = "오후 하늘형태")
  private String pmSkyForm;
  @ApiModelProperty(notes = "오후 강수형태")
  private String pmPrecipitationForm;

  @ApiModelProperty(notes = "일 최고기온")
  private String temperatureHigh;
  @ApiModelProperty(notes = "일 최저기온")
  private String temperatureLow;


  @ApiModelProperty(notes = "오전 날씨형태 아이콘 코드")
  public String getAmWeatherIconCode(){
    WeatherForecastForm form = WeatherForecastForm.getByName(amWfFormName);
    return form != null ? form.getWeatherIconCode() : "";
  }

  @ApiModelProperty(notes = "오후 날씨형태 아이콘 코드")
  public String getPmWeatherIconCode(){
    WeatherForecastForm form = WeatherForecastForm.getByName(pmWfFormName);
    return form != null ? form.getWeatherIconCode() : "";
  }

}
