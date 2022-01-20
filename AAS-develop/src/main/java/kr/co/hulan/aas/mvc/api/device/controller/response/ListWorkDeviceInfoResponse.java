package kr.co.hulan.aas.mvc.api.device.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.WorkDeviceInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListWorkDeviceInfoResponse", description="현장 디바이스 정보 검색 응답")
public class ListWorkDeviceInfoResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "현장 디바이스 정보 리스트")
    private List<WorkDeviceInfoDto> list;
}
