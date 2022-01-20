package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosSafetyHookCurrentStateResponse", description="IMOS 안전고리 실시간 현황 정보 응답")
public class ImosSafetyHookCurrentStateResponse {

  @ApiModelProperty(notes = "안전고리 사용 근로자수 (안전고리 디바이스 연결 근로자수)")
  private Long pairingCnt;

  @ApiModelProperty(notes = "작업 근로자수 (감지구역 진입 근로자수)")
  private Long districtWorkerCnt;

  @ApiModelProperty(notes = "안전고리 체결 근로자수")
  private Long lockCnt;

  @ApiModelProperty(notes = "안전고리 미체결 근로자수")
  public Long getUnlockCnt(){
    if( districtWorkerCnt != null && lockCnt != null ){
      return districtWorkerCnt - lockCnt;
    }
    return 0L;
  }

}
