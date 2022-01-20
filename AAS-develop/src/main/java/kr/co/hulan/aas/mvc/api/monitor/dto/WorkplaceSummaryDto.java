package kr.co.hulan.aas.mvc.api.monitor.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorkplaceSummaryDto", description="현장 요약 정보")
public class WorkplaceSummaryDto {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명(공사명)")
  private String wpName;
  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;
  @ApiModelProperty(notes = "건설사명")
  private String ccName;
  @ApiModelProperty(notes = "현장 관리자 아이디")
  private String manMbId;
  @ApiModelProperty(notes = "현장 관리자 명")
  private String manMbName;
  @ApiModelProperty(notes = "현장 시도명")
  private String wpSido;
  @ApiModelProperty(notes = "현장 시군구명")
  private String wpGugun;
  @ApiModelProperty(notes = "현장 주소")
  private String wpAddr;
  @ApiModelProperty(notes = "현장 전화번호")
  private String wpTel;

}
