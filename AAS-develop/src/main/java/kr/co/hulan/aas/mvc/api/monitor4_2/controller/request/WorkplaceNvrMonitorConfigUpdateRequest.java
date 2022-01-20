package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.validator.EnumCodeValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorkplaceNvrMonitorConfigUpdateRequest", description="현장 NVR 설정 정보 변경 요청")
public class WorkplaceNvrMonitorConfigUpdateRequest {

  @NotNull(message = "채널 Event 수신 여부는 필수입니다.")
  @EnumCodeValid(target = EnableCode.class, message = "채널 Event 수신 여부값이 올바르지 않습니다.")
  @ApiModelProperty(notes = "채널 Event 수신 ON/OFF. 0: OFF, 1: ON", required = true)
  private Integer nvrEvent;

}
