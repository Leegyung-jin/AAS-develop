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
@ApiModel(value="UpdateActionWorkplaceBoardRequest", description="현장 게시판 조치 처리 요청")
public class UpdateActionWorkplaceBoardRequest {

    @NotNull
    @ApiModelProperty(notes = "현장 게시판 아이디", required = true)
    private Integer wrId;

    @NotEmpty
    @ApiModelProperty(notes = "승인결과 ( 현장관리자 필수의 경우 '승인' , '미승인', 협력사의 경우 '결과입력' ). ")
    private String wr6;

    @NotNull
    @ApiModelProperty(notes = "조치내용")
    private String wr11;

    @Size(max=3)
    @ApiModelProperty(notes = "첨부 파일 리스트")
    private List<UploadAttachedFileDto> wrFiles;
}
