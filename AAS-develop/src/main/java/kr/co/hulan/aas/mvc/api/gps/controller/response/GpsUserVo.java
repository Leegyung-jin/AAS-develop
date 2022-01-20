package kr.co.hulan.aas.mvc.api.gps.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="GpsUserVo", description="GPS 유저 정보")
@AllArgsConstructor
@NoArgsConstructor
public class GpsUserVo {

  @ApiModelProperty(notes = "현장아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;
  @ApiModelProperty(notes = "근로자 아이디(전화번호)")
  private String mbId;
  @ApiModelProperty(notes = "성명")
  private String mbName;
  @ApiModelProperty(notes = "근로자 분류명. 근로자, 장비기사, 덤프기사 등등")
  private String mbLevelName;
  @ApiModelProperty(notes = "경도")
  private Double longitude;
  @ApiModelProperty(notes = "위도")
  private Double latitude;
  @ApiModelProperty(notes = "측정 시간")
  private java.util.Date measureTime;


}
