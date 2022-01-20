package kr.co.hulan.aas.mvc.api.main.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListWorkerResponse;
import kr.co.hulan.aas.mvc.model.dto.G5BoardNewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AdminMainResponse", description="어드민 메인 데이터 응답")
public class AdminMainResponse {

    @ApiModelProperty(notes = "최근 신규가입 근로자 리스트")
    private ListWorkerResponse listWorkerResponse;

    @ApiModelProperty(notes = "최근 게시물 리스트")
    private List<G5BoardNewDto> boardList;

}
