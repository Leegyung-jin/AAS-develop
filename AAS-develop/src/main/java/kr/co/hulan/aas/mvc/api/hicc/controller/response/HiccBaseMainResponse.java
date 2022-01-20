package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccBaseInfoVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccMemberUiComponentVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSidoVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccBaseMainResponse", description="메인 정보( UI 구성정보 )")
public class HiccBaseMainResponse {

  @ApiModelProperty(notes = "통합 관제 기본 정보")
  private HiccBaseInfoVo baseInfo;

  @ApiModelProperty(notes = "컴포넌트 구성 정보")
  private List<HiccMemberUiComponentVo> uiComponentList;

  @ApiModelProperty(notes = "시도별 현장 요약 정보 리스트")
  private List<HiccWorkplaceSummaryPerSidoVo> list;

}
