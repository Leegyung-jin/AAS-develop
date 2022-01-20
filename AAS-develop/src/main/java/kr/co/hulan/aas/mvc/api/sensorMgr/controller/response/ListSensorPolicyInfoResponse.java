package kr.co.hulan.aas.mvc.api.sensorMgr.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.SensorPolicyInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorPolicyInfoResponse", description="센서 정책 리스트 응답")
public class ListSensorPolicyInfoResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "센서 정책 정보 리스트")
    private List<SensorPolicyInfoDto> list;
}
