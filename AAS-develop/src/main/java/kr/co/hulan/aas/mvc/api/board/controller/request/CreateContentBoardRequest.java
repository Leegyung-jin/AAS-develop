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
@ApiModel(value="CreateContentBoardRequest", description="내용 생성 요청")
public class CreateContentBoardRequest {

    @NotEmpty
    @ApiModelProperty(notes = "게시판 제목", required = true)
    private String wrSubject;
}
