package kr.co.hulan.aas.mvc.api.monitor.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CommonMonitoringRequest", description="일반 모니터링 정보 제공 요청")
public class CommonMonitoringRequest {

    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @ApiModelProperty(notes = "현장 관리자 아이디", required = true)
    private String mbId;

    @ApiModelProperty(notes = "GPS 정보 제외. 0 : N, 1 : Y", required = true)
    private Integer gpsExclude;

}
