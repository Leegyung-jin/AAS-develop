package kr.co.hulan.aas.mvc.api.bls.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor.dto.WorkplaceSummaryDto;
import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceMonitorConfigDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MonitoringWokspaceResponse", description="BLE 모니터링 현장 정보 응답")
public class MonitoringWokspaceResponse {

  @ApiModelProperty(notes = "현장 요약 정보")
  private WorkplaceSummaryDto workplace;

  @ApiModelProperty(notes = "현장 설정 정보")
  private WorkPlaceMonitorConfigDto monitorConfig;

  @ApiModelProperty(notes = "현장 빌딩 리스트 정보")
  private List<BuildingDto> buildingList;

}
