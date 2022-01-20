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
@ApiModel(value="HiccRiskAccessmentStateVo", description="위험성 평가 현황 현황")
public class HiccRiskAccessmentStateVo {

  @ApiModelProperty(notes = "접수 건수(총 건수)")
  private Integer totalCount;

  @ApiModelProperty(notes = "처리 건수(완료 건수)")
  private Integer actionCount;

  @ApiModelProperty(notes = "미처리 건수")
  public Integer getNotActionCount(){
    if( totalCount != null && actionCount != null && totalCount >= actionCount ){
      return totalCount - actionCount;
    }
    return 0;
  };
}
