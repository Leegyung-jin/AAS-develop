package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSafeyScoreVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccSafetyScoreStateResponse", description="통합 관제 안전점수 현황 응답")
public class HiccSafetyScoreStateResponse {

  private List<HiccWorkplaceSafeyScoreVo> list;

  private List<HiccWorkplaceSafeyScoreVo> averageList;

}
