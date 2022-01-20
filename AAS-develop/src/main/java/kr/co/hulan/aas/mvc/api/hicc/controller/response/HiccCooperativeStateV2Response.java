package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeStateVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.SectionStatVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccCooperativeStateResponse", description="통합 관제 협력사 현황 V2 응답")
public class HiccCooperativeStateV2Response {

  @ApiModelProperty(notes = "협력사 현황")
  private CooperativeStateVo coopState;

  @ApiModelProperty(notes = "공종별 협력사 풀 리스트")
  private List<SectionStatVo> sectionList;

}
