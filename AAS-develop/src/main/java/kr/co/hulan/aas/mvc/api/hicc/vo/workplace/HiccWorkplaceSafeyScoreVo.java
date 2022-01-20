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
@ApiModel(value="HiccWorkplaceSafeyScoreVo", description="현장 안전 점수")
public class HiccWorkplaceSafeyScoreVo {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "평가 점수")
  private Integer score;
}
