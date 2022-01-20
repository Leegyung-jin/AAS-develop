package kr.co.hulan.aas.mvc.api.sensorMgr.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationByWorkplaceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorSituationByWorkPlaceResponse", description="현장별 센서 현황 리스트 응답")
public class ListSensorSituationByWorkPlaceResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "개별 센서 정보 리스트")
    private List<SensorSituationByWorkplaceDto> list;
}
