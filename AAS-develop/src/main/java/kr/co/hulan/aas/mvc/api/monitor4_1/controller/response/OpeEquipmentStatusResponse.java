package kr.co.hulan.aas.mvc.api.monitor4_1.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EquipmentSituationVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OpeEquipmentStatusResponse", description="장비 및 차량 현황 정보 응답")
public class OpeEquipmentStatusResponse {

  @ApiModelProperty(notes = "장비 및 차량 현황 정보")
  private List<EquipmentSituationVo> equipmentList;

  @ApiModelProperty(notes = "전체 등록 수")
  public Long getTotalCount(){
    if( equipmentList != null && equipmentList.size() > 0 ){
      return equipmentList.stream().map( equipment -> equipment.getTotalCount()).reduce(0L, Long::sum);
    }
    return 0L;
  }

  @ApiModelProperty(notes = "전체 운영 수")
  public Long getOpeCount(){
    if( equipmentList != null && equipmentList.size() > 0 ){
      return equipmentList.stream().map( equipment -> equipment.getOpeCount()).reduce(0L, Long::sum);
    }
    return 0L;
  }


}
