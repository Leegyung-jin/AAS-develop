package kr.co.hulan.aas.mvc.api.monitor4_1.vo;

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
@ApiModel(value="CommuteTypeSummary", description="출근 유형별 현황 정보")
public class CommuteTypeSummary {

  @ApiModelProperty(notes = "총 출력인원")
  private Long totalWorkerCount;

  @ApiModelProperty(notes = "QR 이용 출력인원")
  private Long qrEnterCount;

  @ApiModelProperty(notes = "APP 이용 출력인원 (BLE Scan/Geofence 진입 등)")
  private Long appEnterCount;

  @ApiModelProperty(notes = "Tracker 이용 출력인원")
  private Long trackerEnterCount;
}
