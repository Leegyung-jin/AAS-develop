package kr.co.hulan.aas.mvc.api.monitor4_1.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor.dto.EnvironmentMeasureDeviceVo;
import kr.co.hulan.aas.mvc.model.dto.GasLogDto;
import kr.co.hulan.aas.mvc.model.dto.GasSafeRangeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HazardStatusResponse", description="유해물질 측정 상세 정보 응답")
public class HazardStatusResponse {

  @ApiModelProperty(notes = "현재 수치")
  private EnvironmentMeasureDeviceVo current;

  @ApiModelProperty(notes = "최근 수치")
  private List<GasLogDto> historyList;

  @ApiModelProperty(notes = "측정 항목별 안전기준 정보")
  private List<GasSafeRangeDto> safeRangeList;
}
