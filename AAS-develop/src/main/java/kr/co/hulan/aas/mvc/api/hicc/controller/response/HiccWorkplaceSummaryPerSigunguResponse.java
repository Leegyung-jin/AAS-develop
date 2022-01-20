package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSigunguVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceSummaryPerSigunguResponse", description="시도 내 시군구별 현장 요약 리스트 응답")
public class HiccWorkplaceSummaryPerSigunguResponse {

  @ApiModelProperty(notes = "시도 코드")
  private String sidoCd;

  @ApiModelProperty(notes = "시도내 시군구별 현장 현황 리스트")
  private List<HiccWorkplaceSummaryPerSigunguVo> list;

  @ApiModelProperty(notes = "전체 현장 수")
  public int getWorkplaceCount(){
    int count = 0;
    if( list != null && list.size() > 0 ){
      for(HiccWorkplaceSummaryPerSigunguVo summary : list ){
        count += summary.getWorkplaceCount();
      }
    }
    return count;
  };

}
