package kr.co.hulan.aas.mvc.api.hicc.vo.risk;

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
@ApiModel(value="HiccRiskAccessmentItemVo", description="위험성 평가 위험 요인 정보")
public class HiccRiskAccessmentItemVo {

  /*
  @ApiModelProperty(notes = "위험성 평가 위험 요인 번호")
  private Long raItemNo;
   */
  @ApiModelProperty(notes = "공종")
  private String section;
  @ApiModelProperty(notes = "작업명")
  private String workName;
  @ApiModelProperty(notes = "작업내용")
  private String workDetail;
  @ApiModelProperty(notes = "위험 요인")
  private String riskFactor;
  @ApiModelProperty(notes = "발생형태")
  private String occurType;
  @ApiModelProperty(notes = "안전대책")
  private String safetyMeasure;

}
