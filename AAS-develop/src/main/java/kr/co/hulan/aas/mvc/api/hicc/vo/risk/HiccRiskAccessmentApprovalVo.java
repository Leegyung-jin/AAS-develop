package kr.co.hulan.aas.mvc.api.hicc.vo.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import kr.co.hulan.aas.common.code.RiskAccessmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccRiskAccessmentApprovalVo", description="위험성 평가 안전점검 결재 정보")
public class HiccRiskAccessmentApprovalVo {

  @ApiModelProperty(notes = "위험성 평가 결재 번호")
  private Long raiApprvlNo;
  @ApiModelProperty(notes = "위험성 평가 안전점검 번호")
  private Long raiNo;
  @ApiModelProperty(notes = "행위. 1: 승인요청, 2: 승인(완료), 3: 반려")
  private Integer action;
  @ApiModelProperty(notes = "결재 비고")
  private String comment;
  @ApiModelProperty(notes = "생성일(처리일)")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자(처리자) 아이디")
  private String creator;
  @ApiModelProperty(notes = "생성자(처리자)명")
  private String creatorName;

  @ApiModelProperty(notes = "행위명")
  public String getActionName(){
    RiskAccessmentStatus status = RiskAccessmentStatus.get(action);
    return status != null ? status.getName() : "";
  }

  /*
  @ApiModelProperty(notes = "승인(완료)일")
  public java.util.Date getApprovalDate(){
    if( RiskAccessmentStatus.get(action) == RiskAccessmentStatus.APPROVAL ){
      return createDate;
    }
    return null;
  };

  @ApiModelProperty(notes = "반려일")
  public java.util.Date getRejectDate(){
    if( RiskAccessmentStatus.get(action) == RiskAccessmentStatus.READY ){
      return createDate;
    }
    return null;
  };

  @ApiModelProperty(notes = "승인요청일")
  public java.util.Date getRequestApprovalDate(){
    if( RiskAccessmentStatus.get(action) == RiskAccessmentStatus.REQUEST_APPROVAL ){
      return createDate;
    }
    return null;
  };
   */


}
