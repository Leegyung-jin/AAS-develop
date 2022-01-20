package kr.co.hulan.aas.mvc.api.board.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.board.dto.UploadAttachedFileDto;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateWorkplaceBoardRequest", description="현장 게시판 수정 요청")
public class UpdateWorkplaceBoardRequest {

    @NotNull
    @ApiModelProperty(notes = "현장 게시판 아이디", required = true)
    private Integer wrId;

    @NotEmpty
    @ApiModelProperty(notes = "현장 게시판 글 분류, 현장관리자의 경우 '안전' 내지는 '공사', 협력사의 경우 '승인요청' ", required = true)
    private String caName;

    @NotEmpty
    @ApiModelProperty(notes = "현장 게시판 글 제목", required = true)
    private String wrSubject;

    @NotEmpty
    @ApiModelProperty(notes = "현장 게시판 글 내용", required = true)
    private String wrContent;

    @Size(max=3)
    @ApiModelProperty(notes = "첨부 파일 리스트")
    private List<UploadAttachedFileDto> wrFiles;

}