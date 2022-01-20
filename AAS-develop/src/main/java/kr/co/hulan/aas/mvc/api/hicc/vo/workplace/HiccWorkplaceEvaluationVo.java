package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceEvaluationVo", description="현장 평가 정보")
public class HiccWorkplaceEvaluationVo extends HiccWorkplaceSafeyScoreVo {

  @ApiModelProperty(notes = "전체 현장 내 평가 순위")
  private Integer rankingInWorkplace;

  @ApiModelProperty(notes = "전체 현장수")
  private Integer workplaceCount;

}
