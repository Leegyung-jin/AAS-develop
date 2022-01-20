package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceSummaryPerSigunguVo", description="시군구별 현장 요약 정보 리스트")
public class HiccWorkplaceSummaryPerSigunguVo {

  @ApiModelProperty(notes = "시군구 코드")
  private String sigunguCd;
  @ApiModelProperty(notes = "시군구 전체명")
  private String sigunguNmFull;

  @ApiModelProperty(notes = "지도 내 X 좌표")
  private Double posX;
  @ApiModelProperty(notes = "지도 내 Y 좌표")
  private Double posY;

  @ApiModelProperty(notes = "시군구 내 현장 요약 정보 리스트")
  private List<HiccWorkplaceSummaryVo> list;

  @ApiModelProperty(notes = "시군구 내 현장 수")
  public int getWorkplaceCount(){
    int count = 0;
    if( list != null && list.size() > 0 ){
      count = list.size();
    }
    return count;
  }

}
