package kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorkplaceNvrMonitorConfigVo", description="현장 NVR 관련 설정 정보")
public class WorkplaceNvrMonitorConfigVo {

  @ApiModelProperty(notes = "채널 Event 수신 ON/OFF. 0: OFF, 1: ON")
  private Integer nvrEvent = 0;
}
