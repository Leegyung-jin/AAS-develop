package kr.co.hulan.aas.mvc.api.workplace.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceWorkerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListWorkplaceWorkerResponse", description="근로자 현장 편입 리스트 응답")
public class ListWorkplaceWorkerResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "현장 리스트")
    private List<WorkPlaceWorkerDto> list;
}
