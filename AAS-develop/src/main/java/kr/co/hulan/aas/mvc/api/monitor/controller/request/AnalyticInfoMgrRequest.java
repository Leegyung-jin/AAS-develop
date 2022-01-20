package kr.co.hulan.aas.mvc.api.monitor.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AnalyticInfoMgrRequest", description="영상 감지 정보 OFF 요청")
public class AnalyticInfoMgrRequest {

  @NotEmpty(message = "현장 아이디가 존재하지 않습니다.")
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @NotEmpty(message = "macAddress 가 존재하지 않습니다.")
  @ApiModelProperty(notes = "macAddress", required = true)
  private String macAddress;

  @NotNull(message = "이벤트 구분이 존재하지 않습니다.")
  @ApiModelProperty(notes = "이벤트 구분, 1: 안전모, 2: 화재", required = true)
  private Integer eventType;

  @NotNull(message = "이벤트 노출 상태가 존재하지 않습니다.")
  @ApiModelProperty(notes = "이벤트 노출. 0: OFF, 1: ON", required = true)
  private Integer eventView;
}
