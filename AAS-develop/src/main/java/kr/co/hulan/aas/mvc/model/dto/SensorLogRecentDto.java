package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="SensorLogRecentDto", description="사용자 최근 센서 로그")
@AllArgsConstructor
@NoArgsConstructor
public class SensorLogRecentDto {

  @ApiModelProperty(notes = "최고 층수")
  private String mbId;

  @ApiModelProperty(notes = "최고 층수")
  private java.util.Date slrDatetime;

  @ApiModelProperty(notes = "최고 층수")
  private String wpId;

  @ApiModelProperty(notes = "최고 층수")
  private Integer siIdx;

  @ApiModelProperty(notes = "최고 층수")
  private String coopMbId;

  @ApiModelProperty(notes = "최고 층수")
  private Integer inOutType;
}
