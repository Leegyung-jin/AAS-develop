package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="UiComponentDto", description="UI Component 정보")
@AllArgsConstructor
@NoArgsConstructor
public class UiComponentDto {

  @ApiModelProperty(notes = "컴포넌트 아이디")
  private String cmptId;
  @ApiModelProperty(notes = "컴포넌트명")
  private String cmptName;
  @ApiModelProperty(notes = "설명")
  private String description;
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용")
  private Integer status;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "최종 수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "최종 수정자")
  private String updater;


  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일명")
  private String fileName;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일 원본명")
  private String fileNameOrg;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일 저장 위치(상대경로)")
  private String filePath;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일 저장소. 0: local Storage ")
  private Integer fileLocation;

  @ApiModelProperty(notes = "대표 파일")
  public UploadedFile getRepresentativeFile(){
    if(StringUtils.isNotEmpty(fileName)
        && StringUtils.isNotEmpty(filePath)
        && fileLocation != null ){
      Storage storage = Storage.get(fileLocation);
      if( storage != null ){
        return new UploadedFile(
            fileName, fileNameOrg
        );
      }
    }
    return null;
  }

  @ApiModelProperty(notes = "대표 파일 다운로드 URL")
  public String getRepresentativeFileDownloadUrl(){
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
