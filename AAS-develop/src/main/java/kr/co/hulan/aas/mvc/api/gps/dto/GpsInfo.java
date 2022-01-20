package kr.co.hulan.aas.mvc.api.gps.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="GpsInfo", description="GPS 정보")
public class GpsInfo {

    @ApiModelProperty(notes = "근로자 아이디 (휴대폰 번호)")
    private String mbId;

    @ApiModelProperty(notes = "근로자 정보")
    private WorkerInfo workerInfo;

    @ApiModelProperty(notes = "현장 근로자 GPS 측위 정보")
    private GpsLocation gpsLocation;

    @ApiModelProperty(notes = "장비 정보")
    private EquipmentInfo equipmentInfo;

}
