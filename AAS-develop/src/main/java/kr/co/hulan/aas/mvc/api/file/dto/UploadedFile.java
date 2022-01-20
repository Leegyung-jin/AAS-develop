package kr.co.hulan.aas.mvc.api.file.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="UploadedFile", description="업로드 파일 정보")
@AllArgsConstructor
@NoArgsConstructor
public class UploadedFile {
    @ApiModelProperty(notes = "저장된 파일명")
    private String fileName;
    @ApiModelProperty(notes = "업로드 원본 파일명")
    private String fileOriginalName;
}
