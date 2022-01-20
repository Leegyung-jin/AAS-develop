package kr.co.hulan.aas.mvc.api.gps.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SearchAttendantSituationResponse", description="협력사별 출력인원 조회 응답")
public class SearchAttendantSituationResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "협력사별 출력인원 현황 리스트")
    private List<AttendantSituationVo> list;
}
