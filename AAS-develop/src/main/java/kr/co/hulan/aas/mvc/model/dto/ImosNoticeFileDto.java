package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosNoticeFileDto", description="현장관제 공지 첨부파일 정보")
public class ImosNoticeFileDto {

  @ApiModelProperty(notes = "현장관제 공지 첨부파일번호")
  private Long fileNo;

  @ApiModelProperty(notes = "현장관제 공지번호")
  private Long imntNo;

  @NotBlank(message = "파일명은 필수입니다.")
  @ApiModelProperty(notes = "파일명", hidden = true)
  private String fileName;

  @NotBlank(message = "파일 원본명은 필수입니다.")
  @ApiModelProperty(notes = "파일 원본명", hidden = true)
  private String fileNameOrg;

  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "파일 저장 위치(상대경로)", hidden = true)
  private String filePath;

  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "파일 저장소. 0: local Storage ", hidden = true)
  private Integer fileLocation;

  @ApiModelProperty(notes = "파일 다운로드 Url")
  public String getFileDownloadUrl(){
    if(StringUtils.isNotEmpty(fileName)
        && StringUtils.isNotEmpty(filePath)
        && fileLocation != null ){
      Storage storage = Storage.get(fileLocation);
      if( storage != null ){
        StringBuilder sb = new StringBuilder();
        sb.append(storage.getDownloadUrlBase());
        sb.append(filePath);
        sb.append(fileName);
        return sb.toString();
      }
    }
    return "";
  }
}
