package kr.co.hulan.aas.mvc.api.monitor.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateGasDevicePopupRequest", description="위험물질 디바이스 경고 팝업 On/Off 요청")
public class UpdateGasDevicePopupRequest {

  @NotEmpty(message="현장 아이디는 필수입니다.")
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @NotEmpty(message="mac address 는 필수입니다.")
  @ApiModelProperty(notes = "mac address")
  private String macAddress;

  @ApiModelProperty(notes = "On/Off 여부. 0:OFF, 1:ON")
  private Integer dashboardPopup;


}
