package kr.co.hulan.aas.mvc.api.sensorMgr.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateSensorPolicyInfoRequest", description="센서 정책 정보 수정 요청")
public class UpdateSensorPolicyInfoRequest {

    @NotNull
    @ApiModelProperty(notes = "센서 정책 정보 아이디", required = true)
    private Integer spIdx;
    @NotNull
    @ApiModelProperty(notes = "스캔 주기", required = true)
    private Integer scanInterval;
    @NotNull
    @ApiModelProperty(notes = "스캔 idle 주기", required = true)
    private Integer idleInterval;
    @NotNull
    @ApiModelProperty(notes = "스캔 결과 보고 주기", required = true)
    private Integer reportInterval;
}
