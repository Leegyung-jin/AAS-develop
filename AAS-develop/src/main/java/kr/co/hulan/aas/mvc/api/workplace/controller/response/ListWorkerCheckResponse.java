package kr.co.hulan.aas.mvc.api.workplace.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.WorkerCheckDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListWorkerCheckResponse", description="고위험/주요근로자 리스트 응답")
public class ListWorkerCheckResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "고위험/주요근로자 리스트")
    private List<WorkerCheckDto> list;
}
