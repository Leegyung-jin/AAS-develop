package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccSidoWeatherVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceSummaryPerSidoVo", description="시도별 현장 요약 정보 리스트")
public class HiccWorkplaceSummaryPerSidoVo {

  @ApiModelProperty(notes = "시도 코드")
  private String sidoCd;
  @ApiModelProperty(notes = "시도명")
  private String sidoNm;
  @ApiModelProperty(notes = "시도명(2자리 짧은명)")
  private String sidoShortNm;
  @ApiModelProperty(notes = "순서 (오름차순)")
  private String sidoOrd;

  @ApiModelProperty(notes = "시도 날씨 및 대기질 정보")
  private HiccSidoWeatherVo weatherInfo;

  @ApiModelProperty(notes = "시도 내 현장 요약 정보 리스트")
  private List<HiccWorkplaceSummaryVo> list;

  @ApiModelProperty(notes = "시도 내 현장 수")
  public int getWorkplaceCount(){
    int count = 0;
    if( list != null && list.size() > 0 ){
      count = list.size();
    }
    return count;
  }

}
