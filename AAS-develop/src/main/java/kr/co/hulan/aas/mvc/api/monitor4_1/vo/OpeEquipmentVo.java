package kr.co.hulan.aas.mvc.api.monitor4_1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OpeEquipmentVo", description="운영 장비 정보")
public class OpeEquipmentVo {

  @ApiModelProperty(notes = "식별자(GPS식별자)")
  private String dataKey;
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
  @ApiModelProperty(notes = "장비 코드")
  private Integer equipmentType;
  @ApiModelProperty(notes = "장비명")
  private String equipmentName;
  @ApiModelProperty(notes = "장비 번호 (차량번호)")
  private String equipmentNo;
  @ApiModelProperty(notes = "운행 유형. 0: 상시, 1: 기간제")
  private Integer opeType;
  @ApiModelProperty(notes = "운행 시작일")
  private java.util.Date opeStart;
  @ApiModelProperty(notes = "운행 종료일")
  private java.util.Date opeEnd;
}
