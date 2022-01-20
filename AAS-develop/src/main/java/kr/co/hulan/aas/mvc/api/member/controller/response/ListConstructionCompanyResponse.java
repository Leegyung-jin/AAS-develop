package kr.co.hulan.aas.mvc.api.member.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.member.dto.ConstructionCompanyDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="ListConstructionCompanyResponse", description="건설사 리스트 응답")
public class ListConstructionCompanyResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "건설사 리스트")
    private List<ConstructionCompanyDto> list;
}
