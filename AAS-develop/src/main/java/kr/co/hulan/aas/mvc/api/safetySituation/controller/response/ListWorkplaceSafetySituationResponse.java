package kr.co.hulan.aas.mvc.api.safetySituation.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkPlaceSafetySituationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="ListWorkplaceSafetySituationResponse", description="현장별 안전관리 현황 검색 응답")
public class ListWorkplaceSafetySituationResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "현장별 안전관리 현황 리스트")
    private List<WorkPlaceSafetySituationDto> list;

}
