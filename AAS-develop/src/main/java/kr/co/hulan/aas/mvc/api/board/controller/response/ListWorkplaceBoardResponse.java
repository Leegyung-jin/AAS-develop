package kr.co.hulan.aas.mvc.api.board.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.board.dto.WpBoardDto;
import kr.co.hulan.aas.mvc.model.dto.G5WriteWpboardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListWorkplaceBoardResponse", description="현장 게시판 리스트 응답")
public class ListWorkplaceBoardResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "현장 게시판 리스트")
    private List<WpBoardDto> list;

}
