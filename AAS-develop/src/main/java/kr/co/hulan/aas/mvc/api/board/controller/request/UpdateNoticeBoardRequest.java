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
@ApiModel(value="UpdateNoticeBoardRequest", description="근로자 공지사항 수정 요청")
public class UpdateNoticeBoardRequest {

    @NotNull
    @ApiModelProperty(notes = "공지사항 아이디", required = true)
    private Integer wrId;

    @NotEmpty
    @ApiModelProperty(notes = "공지사항 제목", required = true)
    private String wrSubject;

    @NotEmpty
    @ApiModelProperty(notes = "공지사항 내용", required = true)
    private String wrContent;

    @ApiModelProperty(notes = "긴급 알림 여부")
    private String wr1;

    @ApiModelProperty(notes = "현장 전체 근로자 공지여부")
    private String allWorkplaceWorkerNotice;

    @ApiModelProperty(notes = "공지여부")
    private String wrNotice;

    @Size(max=6)
    @ApiModelProperty(notes = "첨부 파일 리스트")
    private List<UploadAttachedFileDto> wrFiles;

}
