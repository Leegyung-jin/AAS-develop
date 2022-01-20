package kr.co.hulan.aas.mvc.api.hicc.vo.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccRiskAccessmentInspectItemVo", description="위험성 평가 안전점검 위험 요인 정보")
public class HiccRiskAccessmentInspectItemVo extends HiccRiskAccessmentItemVo {

  @ApiModelProperty(notes = "위험성 평가 위험 요인 항목 번호")
  private Long raiItemNo;
  @ApiModelProperty(notes = "위험성 평가 번호")
  private Long raiNo;
  @ApiModelProperty(notes = "위치")
  private String location;

  @ApiModelProperty(notes = "점검 결과")
  private Integer result;
  @ApiModelProperty(notes = "생성일 정보")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "수정일 정보")
  private java.util.Date updateDate;
}
