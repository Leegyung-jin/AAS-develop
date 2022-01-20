package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosMagneticOpeningStateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosMagneticOpeningStateResponse", description="IMOS 안전고리 실시간 현황 정보 응답")
public class ImosMagneticOpeningStateResponse {


  @ApiModelProperty(notes = "전체 개구부 수")
  private Integer totalCnt;

  @ApiModelProperty(notes = "열린 상태의 개구부 리스트")
  List<ImosMagneticOpeningStateVo> openDeviceList;

  @ApiModelProperty(notes = "열린 상태의 개구부 수")
  public Integer getOpenCnt(){
    if( openDeviceList != null ){
      return openDeviceList.size();
    }
    return 0;
  }
}
