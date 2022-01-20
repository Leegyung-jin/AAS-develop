package kr.co.hulan.aas.mvc.api.device.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateWorkEquipmentInfoRequest", description="현장 장비 정보 수정 요청")
public class UpdateWorkEquipmentInfoRequest {

    @NotNull
    @ApiModelProperty(notes = "현장 장비 번호", required = true)
    private Integer idx;

    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotEmpty
    @ApiModelProperty(notes = "협력사 아이디", required = true)
    private String coopMbId;

    @ApiModelProperty(notes = "근로자 아이디")
    private String mbId;

    @NotNull
    @ApiModelProperty(notes = "장비 코드", required = true)
    private Integer equipmentType;

    @NotEmpty
    @ApiModelProperty(notes = "장비 번호 (차량번호)", required = true)
    private String equipmentNo;

    @ApiModelProperty(notes = "디바이스 식별자")
    private String deviceId;

    /*
    @ApiModelProperty(notes = "고정형 GPS mac address")
    private String macAddress;
     */

    @NotNull(message = "운행 유형 정보는 필수입니다.")
    @ApiModelProperty(notes = "운행 유형. 0: 상시, 1: 기간제")
    private Integer opeType;

    @ApiModelProperty(notes = "운행 시작일")
    private Date opeStart;

    @ApiModelProperty(notes = "운행 종료일")
    private Date opeEnd;

    @ApiModelProperty(notes = "설명")
    private String desc;

    @ApiModelProperty(notes = "cctv 재생 가능한 rtc_url 정보")
    private String rtcUrl;

}
