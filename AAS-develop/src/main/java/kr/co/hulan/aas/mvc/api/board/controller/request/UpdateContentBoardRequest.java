package kr.co.hulan.aas.mvc.api.board.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateContentBoardRequest", description="내용 수정 요청")
public class UpdateContentBoardRequest {

    @NotEmpty
    @ApiModelProperty(notes = "공지사항 아이디", required = true)
    private String coId;

    @NotEmpty
    @ApiModelProperty(notes = "공지사항 제목", required = true)
    private String wrSubject;

}