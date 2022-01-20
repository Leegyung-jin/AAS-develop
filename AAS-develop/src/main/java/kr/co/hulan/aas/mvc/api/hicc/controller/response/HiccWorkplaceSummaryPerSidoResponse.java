package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSidoVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSigunguVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceSummaryPerSidoResponse", description="시도별 현장 요약 리스트 응답")
public class HiccWorkplaceSummaryPerSidoResponse {
  @ApiModelProperty(notes = "시도내 시군구별 현장 현황 리스트")
  private List<HiccWorkplaceSummaryPerSidoVo> list;

  @ApiModelProperty(notes = "전체 현장 수")
  public int getWorkplaceCount(){
    int count = 0;
    if( list != null && list.size() > 0 ){
      for(HiccWorkplaceSummaryPerSidoVo summary : list ){
        count += summary.getWorkplaceCount();
      }
    }
    return count;
  };
}
