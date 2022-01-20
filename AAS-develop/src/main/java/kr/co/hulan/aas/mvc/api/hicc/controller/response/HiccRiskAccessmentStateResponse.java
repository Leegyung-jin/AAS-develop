package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentInspectVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentStateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccRiskAccessmentStateResponse", description="위험성 평가 현황 응답")
public class HiccRiskAccessmentStateResponse {

  @ApiModelProperty(notes = "위험성 평가 현황")
  private HiccRiskAccessmentStateVo state;

  @ApiModelProperty(notes = "위험성 평가 리스트")
  private List<HiccRiskAccessmentInspectVo> list;
}
