package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorkplaceSupportMonitoringResponse", description="모니터링 가능한 현장 리스트 응답")
public class WorkplaceSupportMonitoringResponse {

  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "현장 리스트")
  private List<WorkplaceSupportMonitoringDto> list;
}
