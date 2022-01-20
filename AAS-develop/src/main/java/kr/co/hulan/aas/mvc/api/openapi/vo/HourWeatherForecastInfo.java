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
@ApiModel(value="HourWeatherForecastInfo", description="시간 날씨 정보")
public class HourWeatherForecastInfo {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "예보일")
  private String forecastDay;
  @ApiModelProperty(notes = "예보시간")
  private Integer forecastHour;
  @ApiModelProperty(notes = "강수확률(%)")
  private String rainfall;
  @ApiModelProperty(notes = "강수형태")
  private String precipitationForm;
  @ApiModelProperty(notes = "강수량")
  private String precipitation;
  @ApiModelProperty(notes = "습도(%)")
  private String humidity;
  @ApiModelProperty(notes = "적설량")
  private String amountSnow;
  @ApiModelProperty(notes = "하늘형태")
  private String skyForm;
  @ApiModelProperty(notes = "기온(℃)")
  private String temperature;
  @ApiModelProperty(notes = "풍향(degree)")
  private String windDirection;
  @ApiModelProperty(notes = "풍속(m/s)")
  private String windSpeed;

  @ApiModelProperty(notes = "날씨형태 코드")
  public String getWfForm(){
    WeatherForecastForm form = WeatherForecastForm.get(skyForm, precipitationForm);
    return form != null ? ""+form.getCode() : "";
  }

  @ApiModelProperty(notes = "날씨형태명")
  public String getWfFormName(){
    WeatherForecastForm form = WeatherForecastForm.get(skyForm, precipitationForm);
    return form != null ? form.getName() : "";
  }

}
