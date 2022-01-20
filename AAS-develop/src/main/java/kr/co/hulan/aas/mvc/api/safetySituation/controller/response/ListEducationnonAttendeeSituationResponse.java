package kr.co.hulan.aas.mvc.api.safetySituation.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.EducationNonAttendeeSituationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="ListEducationnonAttendeeSituationResponse", description="안전조회미참석자현황 검색 응답")
public class ListEducationnonAttendeeSituationResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "안전조회미참석자현황 리스트")
    private List<EducationNonAttendeeSituationDto> list;
}
