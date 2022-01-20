package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccWeatherInfoVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceEvaluationVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceStateVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceStateResponse", description="통합 관제 현장 현황 정보 응답")
public class HiccWorkplaceStateResponse {

  @ApiModelProperty(notes = "현장 정보")
  private HiccWorkplaceVo info;

  @ApiModelProperty(notes = "현장 현황 정보")
  private HiccWorkplaceStateVo state;

  @ApiModelProperty(notes = "현장 평가 정보")
  private HiccWorkplaceEvaluationVo evaluation;

  @ApiModelProperty(notes = "현장 날씨 정보")
  private HiccWeatherInfoVo weather;

}
