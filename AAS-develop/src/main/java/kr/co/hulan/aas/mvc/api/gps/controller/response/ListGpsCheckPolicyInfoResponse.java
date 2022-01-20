package kr.co.hulan.aas.mvc.api.gps.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.GpsCheckPolicyInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListGpsCheckPolicyInfoResponse", description="GPS 정책 검색 응답")
public class ListGpsCheckPolicyInfoResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "GPS 정책 정보 리스트")
    private List<GpsCheckPolicyInfoDto> list;
}
