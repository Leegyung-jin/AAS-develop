package kr.co.hulan.aas.mvc.api.orderoffice.vo;

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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OrderingOfficeListVo", description="발주사 요약 정보")
public class OrderingOfficeListVo {

  @ApiModelProperty(notes = "발주사 관리번호")
  private Long officeNo;
  @ApiModelProperty(notes = "발주사명")
  private String officeName;
  @ApiModelProperty(notes = "전화번호")
  private String telephone;
  @ApiModelProperty(notes = "사업자번호")
  private String biznum;
  @ApiModelProperty(notes = "우편번호")
  private String zonecode;
  @ApiModelProperty(notes = "법정동코드")
  private String bcode;
  @ApiModelProperty(notes = "기본주소")
  private String address;
  @ApiModelProperty(notes = "주소상세")
  private String addressDetail;
  @ApiModelProperty(notes = "시도명")
  private String sido;
  @ApiModelProperty(notes = "시군구명")
  private String sigungu;
  @ApiModelProperty(notes = "법정동명")
  private String bname;

  @ApiModelProperty(notes = "통합관제 관리번호")
  private Long hiccNo;
  @ApiModelProperty(notes = "통합관제명")
  private String hiccName;

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

  @ApiModelProperty(notes = "현장수")
  private Integer workplaceCount;

  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "최종수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "최종수정자")
  private String updater;

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
