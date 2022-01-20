package kr.co.hulan.aas.mvc.api.safetySituation.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerFatiqueDto;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerSafetySituationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListWorkerFatiqueResponse", description="근로자 피로도 현황 검색 응답")
public class ListWorkerFatiqueResponse {


    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "좋음 수")
    private long normalCount;
    @ApiModelProperty(notes = "양호 수")
    private long goodCount;
    @ApiModelProperty(notes = "위험 수")
    private long dangerCount;
    @ApiModelProperty(notes = "휴식필요 수")
    private long needRelaxCount;
    @ApiModelProperty(notes = "근로자 피로도 현황 리스트")
    private List<WorkerFatiqueDto> list;
}
