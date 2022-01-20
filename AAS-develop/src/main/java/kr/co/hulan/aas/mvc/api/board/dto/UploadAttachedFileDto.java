package kr.co.hulan.aas.mvc.api.board.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="UploadAttachedFileDto", description="업로드 파일 정보")
@AllArgsConstructor
@NoArgsConstructor
public class UploadAttachedFileDto {

    @NotNull
    @Min(0)
    @ApiModelProperty(notes = "첨부 파일 넘버")
    private Integer bfNo;

    @ApiModelProperty(notes = "저장된 파일명. 공백일 경우 업로드하지 않은 것으로 처리하며 값이 있으면 삭제 Flag 무시")
    private String fileName;

    @ApiModelProperty(notes = "업로드 원본 파일명. 저장된 파일명이 있을 경우 유효")
    private String fileOriginalName;

    @NotEmpty
    @ApiModelProperty(notes = "처리 모드. 0: 변경없음, 1: 삭제, 2: 신규 파일 업로드 ")
    private String bfDelete;

}
