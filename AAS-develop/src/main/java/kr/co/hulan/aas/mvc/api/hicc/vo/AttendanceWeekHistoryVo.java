package kr.co.hulan.aas.mvc.api.hicc.vo;

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
@ApiModel(value="AttendanceWeekHistoryVo", description="일별 출역 통계")
public class AttendanceWeekHistoryVo {

  @ApiModelProperty(notes = "금일 전체 출역인원 수")
  private Long totalWorkerCount;

  @ApiModelProperty(notes = "금주(금일제외 7일) 일별 출역인원 수")
  private List<AttendanceDayStatVo> oneWeekAgoWorkerCountList;

  @ApiModelProperty(notes = "금주(금일제외 7일) 일별 출역인원 수")
  private List<AttendanceDayStatVo> twoWeekAgoWorkerCountList;
}
