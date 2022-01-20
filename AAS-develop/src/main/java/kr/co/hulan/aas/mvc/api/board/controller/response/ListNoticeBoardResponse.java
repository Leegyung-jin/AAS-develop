package kr.co.hulan.aas.mvc.api.board.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.board.dto.NoticeDto;
import kr.co.hulan.aas.mvc.model.dto.G5WriteNoticeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListNoticeBoardResponse", description="근로자 공지사항 리스트 응답")
public class ListNoticeBoardResponse {
    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "근로자 공지사항 리스트")
    private List<NoticeDto> list;
}
