package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

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
@ApiModel(value="HiccWorkplaceSafeyScoreVo", description="평가일 현장 안전 점수")
public class HiccWorkplaceSafeyScorePerDayVo extends HiccWorkplaceSafeyScoreVo {

  @ApiModelProperty(notes = "안전점수 평가일")
  private String safetyDate;

}
