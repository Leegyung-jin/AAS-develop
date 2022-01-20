package kr.co.hulan.aas.mvc.api.workplace.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.WorkerWarnDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListWorkerWarnResponse", description="근로자 경고 리스트 응답")
public class ListWorkerWarnResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "근로자 경고 리스트")
    private List<WorkerWarnDto> list;
}
