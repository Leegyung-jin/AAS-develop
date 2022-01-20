package kr.co.hulan.aas.mvc.api.jobMgr.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.RecruitApplyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListRecruitApplyResponse", description="구직 정보 리스트 응답")
public class ListRecruitApplyResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "구인 정보 리스트")
    private List<RecruitApplyDto> list;
}
