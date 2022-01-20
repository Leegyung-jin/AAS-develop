package kr.co.hulan.aas.mvc.api.sensorLog.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.SensorLogDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListDangerZoneSensorLogResponse", description="위험 지역 접근 기록 리스트 응답")
public class ListDangerZoneSensorLogResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "위험 지역 접근 기록 리스트")
    private List<SensorLogDto> list;
}
