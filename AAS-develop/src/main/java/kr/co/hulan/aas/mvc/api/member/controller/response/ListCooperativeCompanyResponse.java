package kr.co.hulan.aas.mvc.api.member.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.member.dto.CooperativeCompanyDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="ListConstructionCompanyResponse", description="협력사 리스트 응답")
public class ListCooperativeCompanyResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "탈퇴 수")
    private long withdrawCount;
    @ApiModelProperty(notes = "차단 수")
    private long blockCount;
    @ApiModelProperty(notes = "협력사 리스트")
    private List<CooperativeCompanyDto> list;
}
