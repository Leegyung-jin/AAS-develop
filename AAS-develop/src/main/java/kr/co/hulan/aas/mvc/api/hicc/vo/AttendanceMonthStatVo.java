package kr.co.hulan.aas.mvc.api.hicc.vo;

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
@ApiModel(value="AttendanceMonthStatVo", description="월간 출역 현황")
public class AttendanceMonthStatVo {

  @ApiModelProperty(notes = "출역 년월 ( yyyyMM )")
  private String month;
  @ApiModelProperty(notes = "출역인원 수")
  private Integer totalWorkerCount;
  @ApiModelProperty(notes = "안전조회 참석자 수")
  private Integer safetyEduWorkerCount;
  @ApiModelProperty(notes = "앱사용 근로자 수")
  private Integer useAppWorkerCount;

}
