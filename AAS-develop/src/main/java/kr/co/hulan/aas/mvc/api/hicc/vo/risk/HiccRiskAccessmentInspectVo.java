package kr.co.hulan.aas.mvc.api.hicc.vo.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.RiskAccessmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccRiskAccessmentInspectVo", description="위험성 평가 안전점검 요약 정보")
public class HiccRiskAccessmentInspectVo {

  @ApiModelProperty(notes = "위험성 평가 안전점검 번호")
  private Long raiNo;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;
  @ApiModelProperty(notes = "생성일(접수일)")
  private java.util.Date createDate;

  @ApiModelProperty(notes = "공종 요약")
  private String sectionSummary;
  @ApiModelProperty(notes = "발생형태 종류")
  private String occurTypes;

  @ApiModelProperty(notes = "위험요인 수")
  private Integer itemCount;

  @ApiModelProperty(notes = "결재상태. 0:대기, 1: 승인요청, 2: 승인(완료), 3: 반려")
  private Integer status;

  @ApiModelProperty(notes = "처리여부. 0: 미처리, 1: 처리")
  public Integer getCompleted(){
    return RiskAccessmentStatus.get(status) == RiskAccessmentStatus.APPROVAL ?
        EnableCode.ENABLED.getCode() :
        EnableCode.DISABLED.getCode();
  }

}
