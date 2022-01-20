package kr.co.hulan.aas.mvc.api.sensorMgr.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationBySensorTypeDto;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationByWorkplaceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorSituationBySensorTypeResponse", description="센서타입별 센서 현황 리스트 응답")
public class ListSensorSituationBySensorTypeResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "센서타입별 정보 리스트")
    private List<SensorSituationBySensorTypeDto> list;
}
