package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceEvaluationVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSafeyScorePerDayVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSafeyScoreVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceStateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceSafetyScoreStateResponse", description="통합 관제 현장 안전점수 현황 상세 응답")
public class HiccWorkplaceSafetyScoreStateResponse {

  @ApiModelProperty(notes = "현장 현황 정보")
  private HiccWorkplaceStateVo state;

  @ApiModelProperty(notes = "현장 평가 정보")
  private HiccWorkplaceEvaluationVo evaluation;

  @ApiModelProperty(notes = "최근 30일 현장 안전 점수 리스트")
  private List<HiccWorkplaceSafeyScorePerDayVo> list;

  @ApiModelProperty(notes = "최근 30일 현장 누적 평균 안전 점수 리스트")
  private List<HiccWorkplaceSafeyScorePerDayVo> averageList;
}
