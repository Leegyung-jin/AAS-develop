package kr.co.hulan.aas.mvc.api.sensorMgr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="SensorSituationByWorkplaceDto", description="현장별 센서 현황 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorSituationBySensorTypeDto {


    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    @ApiModelProperty(notes = "현장명")
    private String wpName;
    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "센서 타입명")
    private String siType;
    @ApiModelProperty(notes = "센서 설치수")
    private Long sensorCount;
}
