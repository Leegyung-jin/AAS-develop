package kr.co.hulan.aas.mvc.api.workplace.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListWorkplaceResponse", description="현장 리스트 응답")
public class ListWorkplaceResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "현장 리스트")
    private List<WorkPlaceDto> list;

}
