package kr.co.hulan.aas.mvc.api.monitor4_1.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CommuteTypeSummary;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CurrentWorkStatusSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorklogSummaryResponse", description="전체 출력 현황 정보")
public class WorkStatusSummaryResponse {

  @ApiModelProperty(notes = "출근 유형별 현황 정보")
  private CommuteTypeSummary commuteSummary;

  @ApiModelProperty(notes = "현재 작업인원 현황 정보")
  private CurrentWorkStatusSummary currentWorkStatus;

}
