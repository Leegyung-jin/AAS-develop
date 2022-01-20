package kr.co.hulan.aas.mvc.api.sensorMgr.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateSensorInfoRequest", description="센서 정보 수정 요청")
public class UpdateSensorInfoRequest {

    @NotNull
    @ApiModelProperty(notes = "센서 아이디", required = true)
    private Integer siIdx;

    /*
    @NotNull
    @ApiModelProperty(notes = "안전센서번호. 빈값이면 자동 생성")
    private String siCode;


    @NotEmpty
    @ApiModelProperty(notes = "유형", required = true)
    private String siType;
    */

    @NotNull
    @ApiModelProperty(notes = "구역 아이디", required = true)
    private Integer sdIdx;

    @NotNull
    @Min(1)
    @Max(65536)
    @ApiModelProperty(notes = "센서 minor identifier", required = true)
    private Integer minor;

    @NotNull
    @ApiModelProperty(notes = "위치1")
    private String siPlace1;

    @NotNull
    @ApiModelProperty(notes = "위치2")
    private String siPlace2;

    @NotNull
    @ApiModelProperty(notes = "현장관리자 PUSH알림. 0 : 수신안함, 1 :수신 ", required = true)
    private Integer siPush;
}
