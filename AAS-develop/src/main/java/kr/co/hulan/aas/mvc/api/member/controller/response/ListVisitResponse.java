package kr.co.hulan.aas.mvc.api.member.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.member.dto.VisitDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="ListVisitResponse", description="접속로그 리스트 응답")
public class ListVisitResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "접속로그 리스트")
    private List<VisitDto> list;


}
