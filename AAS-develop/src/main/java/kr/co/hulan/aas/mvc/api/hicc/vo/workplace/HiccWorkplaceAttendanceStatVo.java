package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceAttendanceStatVo", description="현장 출역 현황")
public class HiccWorkplaceAttendanceStatVo {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "당일 출역 근로자 수")
  private Integer workerTodayCount;

  @ApiModelProperty(notes = "당월 출역 근로자 수")
  private Integer workerMonthCount;

  @ApiModelProperty(notes = "전체 출역 근로자 수")
  private Integer totalWorkerCount;

}
