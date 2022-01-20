package kr.co.hulan.aas.mvc.api.member.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="ListWorkerResponse", description="근로자 리스트 응답")
public class ListWorkerResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "탈퇴자 수")
    private long withdrawCount;
    @ApiModelProperty(notes = "차단 수")
    private long blockCount;
    @ApiModelProperty(notes = "근로자 리스트")
    private List<WorkerDto> list;
}
