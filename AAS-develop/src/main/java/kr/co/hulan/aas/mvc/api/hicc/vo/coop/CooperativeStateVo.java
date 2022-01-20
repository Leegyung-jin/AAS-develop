package kr.co.hulan.aas.mvc.api.hicc.vo.coop;

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
@ApiModel(value="CooperativeState", description="협력사 현황")
public class CooperativeStateVo {

  @ApiModelProperty(notes = "전체 현장 수")
  private Integer workplaceCount;

  @ApiModelProperty(notes = "전체 협력자 수 (등록)")
  private Integer coopCount;

  @ApiModelProperty(notes = "당일 작업 진행중 협력자 수")
  private Integer activeCoopCount;

  @ApiModelProperty(notes = "당월 작업 진행중 협력자 수")
  private Integer activeMonthCoopCount;

}
