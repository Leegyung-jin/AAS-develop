package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

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
@ApiModel(value="ImosQrGateAttendantStateVo", description="QR Reader 출입시스템 출근자 유형별 현황")
public class ImosQrGateAttendantStateVo {

  @ApiModelProperty(notes = "근로자-QR (QR Reader 이용 근로자수)")
  private Long qrWorkerCount;

  @ApiModelProperty(notes = "관리자 (QR Reader 이용 관리자수)")
  private Long qrManagerCount;

  @ApiModelProperty(notes = "근로자-기타 (기타 출역 근로자수 - QR Reader 미이용)")
  private Long etcWorkerCount;


  @ApiModelProperty(notes = "전체 출역 근로자수")
  private Long totalWorkerCount;
  /*
  public Long getTotalWorkerCnt(){
    long totalCount = 0;
    totalCount += ( qrWorkerCnt == null ? 0 : qrWorkerCnt );
    totalCount += ( qrManagerCnt == null ? 0 : qrManagerCnt );
    totalCount += ( etcWorkerCnt == null ? 0 : etcWorkerCnt );
    return totalCount;
  }
   */


}
