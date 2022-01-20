package kr.co.hulan.aas.mvc.api.component.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import kr.co.hulan.aas.common.code.HulanComponentSiteType;
import kr.co.hulan.aas.common.code.HulanComponentUiType;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HulanUiComponentVo", description="UI 컴보넌트 정보")
public class HulanUiComponentVo {

  @ApiModelProperty(notes = "컴포넌트 아이디")
  private String hcmptId;
  @ApiModelProperty(notes = "컴포넌트명")
  private String hcmptName;
  @ApiModelProperty(notes = "사이트 유형. 1: IMOS, 2: HICC(통합관제)")
  private Integer siteType;
  @ApiModelProperty(notes = "컴포넌트 타입. 1: 메인 UI, 2: 컴포넌트 UI")
  private Integer uiType;
  @ApiModelProperty(notes = "컴포넌트 길이")
  private Integer width;
  @ApiModelProperty(notes = "컴포넌트 높이")
  private Integer height;
  @ApiModelProperty(notes = "컴포넌트 설명")
  private String description;
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용")
  private Integer status;

  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일명", hidden = true)
  private String fileName;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일 원본명", hidden = true)
  private String fileNameOrg;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일 저장 위치(상대경로)", hidden = true)
  private String filePath;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일 저장소. 0: local Storage ", hidden = true)
  private Integer fileLocation;

  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "최종 수정자")
  private String updater;
  @ApiModelProperty(notes = "생성일")
  private Date createDate;
  @ApiModelProperty(notes = "최종수정일")
  private Date updateDate;

  @ApiModelProperty(notes = "사이트 유형명")
  public String getSiteTypeName(){
    HulanComponentSiteType selectedEnum = HulanComponentSiteType.get(siteType);
    return selectedEnum != null ? selectedEnum.getName() : "";
  }

  @ApiModelProperty(notes = "컴포넌트 타입명")
  public String getUiTypeName(){
    HulanComponentUiType selectedEnum = HulanComponentUiType.get(uiType);
    return selectedEnum != null ? selectedEnum.getName() : "";
  }


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
