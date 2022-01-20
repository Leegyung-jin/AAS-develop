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
@ApiModel(value="HiccInfoDto", description="통합 관제 기본 정보")
@AllArgsConstructor
@NoArgsConstructor
public class HiccInfoDto {

  @ApiModelProperty(notes = "통합관제 설정 정보 넘버")
  private Long hiccNo;
  @ApiModelProperty(notes = "통합관제 노출명")
  private String hiccName;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "지도 파일명", hidden = true)
  private String mapFileName;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "지도 원본 파일명", hidden = true)
  private String mapFileNameOrg;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "지도 저장 위치(상대경로)", hidden = true)
  private String mapFilePath;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "지도 저장소. 0: local Storage", hidden = true)
  private Integer mapFileLocation;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "아이콘 파일명", hidden = true)
  private String iconFileName;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "아이콘 원본 파일명", hidden = true)
  private String iconFileNameOrg;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "아이콘 저장 위치(상대경로)", hidden = true)
  private String iconFilePath;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "아이콘 저장소. 0: local Storage ", hidden = true)
  private Integer iconFileLocation;

  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "백그라운드 이미지 파일명", hidden = true)
  private String bgImgFileName;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "백그라운드 이미지 원본 파일명", hidden = true)
  private String bgImgFileNameOrg;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "백그라운드 이미지 저장 위치(상대경로)", hidden = true)
  private String bgImgFilePath;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "백그라운드 이미지 저장소. 0: local Storage ", hidden = true)
  private Integer bgImgFileLocation;

  @ApiModelProperty(notes = "백그라운드 색")
  private String bgColor;

  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자 아이디(mb_id)")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자 아이디(mb_id) ")
  private String updater;

  @ApiModelProperty(notes = "통합관제 노출명")
  public String getTitle(){
    return hiccName;
  }

  @ApiModelProperty(notes = "Icon 파일")
  public UploadedFile getMapFile(){
    if(StringUtils.isNotEmpty(mapFileName)
        && StringUtils.isNotEmpty(mapFilePath)
        && mapFileLocation != null ){
      Storage storage = Storage.get(mapFileLocation);
      if( storage != null ){
        return new UploadedFile(
            mapFileName, mapFileNameOrg
        );
      }
    }
    return null;
  }

  @ApiModelProperty(notes = "map Url")
  public String getMapUrl(){
    if(StringUtils.isNotEmpty(mapFileName)
        && StringUtils.isNotEmpty(mapFilePath)
        && mapFileLocation != null ){
      Storage storage = Storage.get(mapFileLocation);
      if( storage != null ){
        StringBuilder sb = new StringBuilder();
        sb.append(storage.getDownloadUrlBase());
        sb.append(mapFilePath);
        sb.append(mapFileName);
        return sb.toString();
      }
    }
    return "";
  }


  @ApiModelProperty(notes = "Icon 파일")
  public UploadedFile getIconFile(){
    if(StringUtils.isNotEmpty(iconFileName)
        && StringUtils.isNotEmpty(iconFilePath)
        && iconFileLocation != null ){
      Storage storage = Storage.get(iconFileLocation);
      if( storage != null ){
        return new UploadedFile(
            iconFileName, iconFileNameOrg
        );
      }
    }
    return null;
  }

  @ApiModelProperty(notes = "Icon Url")
  public String getIconUrl(){
    if(StringUtils.isNotEmpty(iconFileName)
        && StringUtils.isNotEmpty(iconFilePath)
        && iconFileLocation != null ){
      Storage storage = Storage.get(iconFileLocation);
      if( storage != null ){
        StringBuilder sb = new StringBuilder();
        sb.append(storage.getDownloadUrlBase());
        sb.append(iconFilePath);
        sb.append(iconFileName);
        return sb.toString();
      }
    }
    return "";
  }

  @ApiModelProperty(notes = "background 이미지 파일")
  public UploadedFile getBgImgFile(){
    if(StringUtils.isNotEmpty(bgImgFileName)
        && StringUtils.isNotEmpty(bgImgFilePath)
        && bgImgFileLocation != null ){
      Storage storage = Storage.get(bgImgFileLocation);
      if( storage != null ){
        return new UploadedFile(
            bgImgFileName, bgImgFileNameOrg
        );
      }
    }
    return null;
  }

  @ApiModelProperty(notes = "백그라운드 이미지 Url")
  public String getBgImgUrl(){
    if(StringUtils.isNotEmpty(bgImgFileName)
        && StringUtils.isNotEmpty(bgImgFilePath)
        && bgImgFileLocation != null ){
      Storage storage = Storage.get(bgImgFileLocation);
      if( storage != null ){
        StringBuilder sb = new StringBuilder();
        sb.append(storage.getDownloadUrlBase());
        sb.append(bgImgFilePath);
        sb.append(bgImgFileName);
        return sb.toString();
      }
    }
    return "";
  }
}
