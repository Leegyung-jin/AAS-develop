package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.WorkInOutMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosCommuteTypeStatVo", description="IMOS 출근 유형별 근로자 수 정보")
public class ImosCommuteTypeStatVo {

  @ApiModelProperty(notes = "출근 방법 코드. 2: QRCode, 3: 안면인식, 4: 출입센서, 5: Geofence, 6: GeofencePoligon, 99: 기타")
  private Integer commuteType;

  @ApiModelProperty(notes = "출근자 수")
  private Integer workerCount;

  @ApiModelProperty(notes = "출근 방법명")
  public String getCommuteTypeName(){
    if( commuteType != null ){
      WorkInOutMethod inoutMethod = WorkInOutMethod.get(commuteType);
      if( inoutMethod != null ){
        return inoutMethod.getName();
      }
    }
    return WorkInOutMethod.ETC.getName();
  }

}
