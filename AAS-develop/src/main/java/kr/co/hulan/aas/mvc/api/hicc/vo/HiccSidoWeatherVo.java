package kr.co.hulan.aas.mvc.api.hicc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.PmGrade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccSidoWeatherVo", description="시도 날씨 및 대기질 정보")
public class HiccSidoWeatherVo {

  @ApiModelProperty(notes = "시도 코드")
  private String sidoCd;
  @ApiModelProperty(notes = "습도")
  private String humidity;
  @ApiModelProperty(notes = "풍향")
  private String windDirection;
  @ApiModelProperty(notes = "풍속")
  private String windSpeed;
  @ApiModelProperty(notes = "강수 형태 코드")
  private String precipitationForm;
  @ApiModelProperty(notes = "강수 형태명")
  private String precipitationFormName;
  @ApiModelProperty(notes = "강수량")
  private String precipitation;
  @ApiModelProperty(notes = "하늘형태 코드")
  private String skyForm;
  @ApiModelProperty(notes = "하늘 형태명")
  private String skyFormName;
  @ApiModelProperty(notes = "강수확률")
  private String rainfall;
  @ApiModelProperty(notes = "온도")
  private String temperature;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 지수")
  private String pm25Value;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 지수 등급")
  private String pm25Grade;

  @ApiModelProperty(notes = "초미세먼지(PM2.5) 지수 등급명")
  public String getPm25GradeName(){
    PmGrade pgrade = PmGrade.get(pm25Grade);
    return pgrade != null ? pgrade.getName() : "";
  }
}
