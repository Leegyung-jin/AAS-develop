package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkEquipmentInfoDto", description="현장 장비 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkEquipmentInfoDto {

    @ApiModelProperty(notes = "현장 장비 번호")
    private Integer idx;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "현장명")
    private String wpName;

    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;

    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;

    @ApiModelProperty(notes = "근로자 아이디")
    private String mbId;

    @ApiModelProperty(notes = "근로자명")
    private String mbName;

    @ApiModelProperty(notes = "장비 코드")
    private Integer equipmentType;

    @ApiModelProperty(notes = "장비명")
    private String equipmentName;

    @ApiModelProperty(notes = "장비 번호 (차량번호)")
    private String equipmentNo;

    @ApiModelProperty(notes = "디바이스 식별자")
    private String deviceId;

    @ApiModelProperty(notes = "고정형 GPS mac address")
    private String macAddress;

    @ApiModelProperty(notes = "설명")
    private String desc;

    @ApiModelProperty(notes = "cctv 재생 가능한 rtc_url 정보")
    private String rtcUrl;

    @ApiModelProperty(notes = "운행 유형. 0: 상시, 1: 기간제")
    private Integer opeType;

    @ApiModelProperty(notes = "운행 시작일")
    private Date opeStart;

    @ApiModelProperty(notes = "운행 종료일")
    private Date opeEnd;

    @ApiModelProperty(notes = "등록/변경 일시")
    private java.util.Date updDatetime;

    @ApiModelProperty(notes = "등록/변경자")
    private String updater;
}
