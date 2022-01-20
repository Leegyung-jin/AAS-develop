package kr.co.hulan.aas.mvc.api.sensorTrace.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.SensorLogDto;
import kr.co.hulan.aas.mvc.model.dto.SensorLogTraceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorTraceResponse", description="위치세부기록 리스트 응답")
public class ListSensorTraceResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "위치세부기록 리스트")
    private List<SensorLogTraceDto> list;
}
