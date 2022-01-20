package kr.co.hulan.aas.mvc.api.sensorMgr.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateSensorDistrictRequest", description="센서 구역 정보 생성 요청")
public class CreateSensorDistrictRequest {

    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotEmpty
    @ApiModelProperty(notes = "구역명", required = true)
    private String sdName;

    @ApiModelProperty(notes = "구역표시 기본 색상")
    private String defaultColor;

    @ApiModelProperty(notes = "구역표시 점멸 색상")
    private String flashColor;

}
