package kr.co.hulan.aas.mvc.api.hicc.vo.main;

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
@ApiModel(value="HiccNationWideWorkplaceAllVo", description="전국 현장 현황 정보")
public class HiccNationWideWorkplaceAllVo {

  @ApiModelProperty(notes = "전체 협력사수")
  private Long totalCoopCount;

  @ApiModelProperty(notes = "권역 리스트")
  private List<HiccIntegGroupVo> integGroupList;

  @ApiModelProperty(notes = "전체 현장 수")
  public Long getTotalWorkplaceCount(){
    long sum = 0;
    if(integGroupList != null && integGroupList.size() > 0 ){
      for(HiccIntegGroupVo vo : integGroupList ){
        sum += vo.getTotalWorkplaceCount();
      }
    }
    return sum;
  }

  @ApiModelProperty(notes = "전체 출역인원 수")
  public Long getTotalWorkerCount(){
    long sum = 0;
    if(integGroupList != null && integGroupList.size() > 0 ){
      for(HiccIntegGroupVo vo : integGroupList ){
        sum += vo.getTotalWorkerCount();
      }
    }
    return sum;
  }
}
