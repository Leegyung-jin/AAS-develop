package kr.co.hulan.aas.mvc.api.hicc.vo.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.RiskAccessmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccRiskAccessmentInspectDetailVo", description="위험성 평가 안전점검 상세 정보")
public class HiccRiskAccessmentInspectDetailVo {

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
  @ApiModelProperty(notes = "승인자 아이디")
  private String manMbId;
  @ApiModelProperty(notes = "승인자명")
  private String manMbName;

  @ApiModelProperty(notes = "점검기간 시작일")
  private java.util.Date startDate;
  @ApiModelProperty(notes = "점검기간 종료일")
  private java.util.Date endDate;

  @ApiModelProperty(notes = "생성일(접수일)")
  private java.util.Date createDate;

  @ApiModelProperty(notes = "결재상태. 0:대기, 1: 승인요청, 2: 승인(완료), 3: 반려")
  private Integer status;

  @ApiModelProperty(notes = "마지막 결재 정보")
  private HiccRiskAccessmentApprovalVo approvalInfo;

  @ApiModelProperty(notes = "위험 요인 리스트")
  private List<HiccRiskAccessmentInspectItemVo> itemList;

  @ApiModelProperty(notes = "처리여부. 0: 미처리, 1: 처리")
  public Integer getCompleted(){
    return RiskAccessmentStatus.get(status) == RiskAccessmentStatus.APPROVAL ?
        EnableCode.ENABLED.getCode() :
        EnableCode.DISABLED.getCode();
  }

  @ApiModelProperty(notes = "승인 의견")
  public String approvalComment(){
    if( RiskAccessmentStatus.get(status) ==  RiskAccessmentStatus.APPROVAL ){
      if( approvalInfo != null ){
        return StringUtils.defaultIfBlank(approvalInfo.getComment(), "");
      }
    }
    return "";
  }
}
