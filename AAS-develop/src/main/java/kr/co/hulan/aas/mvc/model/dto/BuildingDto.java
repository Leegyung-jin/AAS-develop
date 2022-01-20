package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Basic;
import javax.persistence.Column;

@Data
@ApiModel(value="BuildingDto", description="현장 빌딩 정보")
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDto {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;
  @ApiModelProperty(notes = "지역 타입. 1: 빌딩, 2: 지상, 3: 지하")
  private Integer areaType;
  @ApiModelProperty(notes = "옥상 포함 여부. 0: 없음, 1: 있음'")
  private Integer containRoof;
  @ApiModelProperty(notes = "갱폼 포함 여부. 0: 없음, 1: 있음")
  private Integer containGangform;

  @ApiModelProperty(notes = "최고 층수")
  private Integer floorUpstair;
  @ApiModelProperty(notes = "최저 층수")
  private Integer floorDownstair;
  @ApiModelProperty(notes = "건물 위치 X 좌표")
  private Integer posX;
  @ApiModelProperty(notes = "건물 위치 Y 좌표")
  private Integer posY;
  @ApiModelProperty(notes = "건물 상황판 위치 X 좌표")
  private Integer boxX;
  @ApiModelProperty(notes = "건물 상황판 위치 Y 좌표")
  private Integer boxY;
  @ApiModelProperty(notes = "디폴트 층도면 파일명")
  private String viewFloorFileName;
  @ApiModelProperty(notes = "디폴트 층도면 원본 파일명")
  private String viewFloorFileNameOrg;
  @ApiModelProperty(notes = "디폴트 층도면 저장 위치(상대경로)")
  private String viewFloorFilePath;
  @ApiModelProperty(notes = "디폴트 층도면 저장소. 0: local Storage ")
  private Integer viewFloorFileLocation;
  @ApiModelProperty(notes = "단면도 파일명")
  private String crossSectionFileName;
  @ApiModelProperty(notes = "단면도 원본 파일명")
  private String crossSectionFileNameOrg;
  @ApiModelProperty(notes = "단면도 저장 위치(상대경로)")
  private String crossSectionFilePath;
  @ApiModelProperty(notes = "단면도 저장소. 0: local Storage ")
  private Integer crossSectionFileLocation;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자 아이디(mb_id) ")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자 아이디(mb_id) ")
  private String updater;

  // Derived Info
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "건설사명")
  private String ccName;
  @ApiModelProperty(notes = "설치된 센서 수")
  private Integer sensorCount;
  @ApiModelProperty(notes = "층이 1개뿐인 경우 해당 층 넘버")
  private Integer oneStoriedFloor;

  @ApiModelProperty(notes = "조감도 파일명")
  private String viewMapFileName;
  @ApiModelProperty(notes = "조감도 원본 파일명")
  private String viewMapFileNameOrg;
  @ApiModelProperty(notes = "조감도 저장 위치(상대경로)")
  private String viewMapFilePath;
  @ApiModelProperty(notes = "조감도 저장소. 0: local Storage ")
  private Integer viewMapFileLocation;

  public UploadedFile getViewMapFile(){
    if(StringUtils.isNotEmpty(viewMapFileName)
        && StringUtils.isNotEmpty(viewMapFilePath)
        && viewMapFileLocation != null ){
      Storage storage = Storage.get(viewMapFileLocation);
      if( storage != null ){
        return new UploadedFile(
            viewMapFileName, viewMapFileNameOrg
        );
      }
    }
    return null;
  }

  public String getViewMapFileDownloadUrl(){
    if(StringUtils.isNotEmpty(viewMapFileName)
        && StringUtils.isNotEmpty(viewMapFilePath)
        && viewMapFileLocation != null ){
      Storage storage = Storage.get(viewMapFileLocation);
      if( storage != null ){
        StringBuilder sb = new StringBuilder();
        sb.append(storage.getDownloadUrlBase());
        sb.append(viewMapFilePath);
        sb.append(viewMapFileName);
        return sb.toString();
      }
    }
    return "";
  }

  public UploadedFile getViewFloorFile(){
    if(StringUtils.isNotEmpty(viewFloorFileName)
            && StringUtils.isNotEmpty(viewFloorFilePath)
            && viewFloorFileLocation != null ){
      Storage storage = Storage.get(viewFloorFileLocation);
      if( storage != null ){
        return new UploadedFile(
                viewFloorFileName, viewFloorFileNameOrg
        );
      }
    }
    return null;
  }

  public String getViewFloorFileDownloadUrl(){
    if(StringUtils.isNotEmpty(viewFloorFileName)
            && StringUtils.isNotEmpty(viewFloorFilePath)
            && viewFloorFileLocation != null ){
      Storage storage = Storage.get(viewFloorFileLocation);
      if( storage != null ){
        StringBuilder sb = new StringBuilder();
        sb.append(storage.getDownloadUrlBase());
        sb.append(viewFloorFilePath);
        sb.append(viewFloorFileName);
        return sb.toString();
      }
    }
    return "";
  }


  public UploadedFile getCrossSectionFile(){
    if(StringUtils.isNotEmpty(crossSectionFileName)
            && StringUtils.isNotEmpty(crossSectionFilePath)
            && crossSectionFileLocation != null ){
      Storage storage = Storage.get(crossSectionFileLocation);
      if( storage != null ){
        return new UploadedFile(
                crossSectionFileName, crossSectionFileNameOrg
        );
      }
    }
    return null;
  }

  public String getCrossSectionFileDownloadUrl(){
    if(StringUtils.isNotEmpty(crossSectionFileName)
            && StringUtils.isNotEmpty(crossSectionFilePath)
            && crossSectionFileLocation != null ){
      Storage storage = Storage.get(crossSectionFileLocation);
      if( storage != null ){
        StringBuilder sb = new StringBuilder();
        sb.append(storage.getDownloadUrlBase());
        sb.append(crossSectionFilePath);
        sb.append(crossSectionFileName);
        return sb.toString();
      }
    }
    return "";
  }

  @ApiModelProperty(notes = "총 층수")
  public Integer getTotalFloor(){
    if( floorUpstair != null
        && floorDownstair != null
        && floorUpstair >=  floorDownstair
    ){
      if( floorUpstair > 0 ){
        return floorDownstair > 0 ?
            floorUpstair - floorDownstair + 1 :
            floorDownstair < 0 ?
                floorUpstair - floorDownstair : floorUpstair;
      }
      else if( floorUpstair == 0 ){
        return -floorDownstair;
      }
      else {
        return Math.abs( floorUpstair - floorDownstair ) + 1;
      }
    }
    return null;
  }


}
