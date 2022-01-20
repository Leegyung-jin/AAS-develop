package kr.co.hulan.aas.mvc.api.safetySituation.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkPrintDto;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerFatiqueDto;
import kr.co.hulan.aas.mvc.model.dto.WorkerWorkPrintDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="ListWorkerWorkPrintResponse", description="출력일보 검색 응답")
public class ListWorkerWorkPrintResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;

    @ApiModelProperty(notes = "출력일보 리스트")
    private List<WorkPrintDto> list;
}
