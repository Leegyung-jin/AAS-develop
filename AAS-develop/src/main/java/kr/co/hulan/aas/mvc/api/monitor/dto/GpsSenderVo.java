package kr.co.hulan.aas.mvc.api.monitor.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="GpsSenderVo", description="GPS 발신자(기기) 정보")
public class GpsSenderVo {


  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;

  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;

  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;

  @ApiModelProperty(notes = "근로자명")
  private String mbName;

  @ApiModelProperty(notes = "근로자 유형명. 근로자, 덤프기사, 장비기사")
  private String mbLevelName;


}
