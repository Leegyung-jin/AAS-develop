package kr.co.hulan.aas.mvc.api.bls.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="FloorSituation", description="현장 빌딩 층 상황")
@AllArgsConstructor
@NoArgsConstructor
public class FloorSituation {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "빌딩 층명")
  private String floorName;

  @ApiModelProperty(notes = "최고 층수")
  private Integer floorUpstair;

  @ApiModelProperty(notes = "최저 층수")
  private Integer floorDownstair;

  @ApiModelProperty(notes = "층도면 파일명")
  private String viewFloorFileName;
  @ApiModelProperty(notes = "층도면 원본 파일명")
  private String viewFloorFileNameOrg;
  @ApiModelProperty(notes = "층도면 저장 위치(상대경로)")
  private String viewFloorFilePath;
  @ApiModelProperty(notes = "층도면 저장소. 0: local Storage ")
  private Integer viewFloorFileLocation;

  @ApiModelProperty(notes = "단면도 층 표시 x 축 좌표 ")
  private Integer crossSectionGridX;

  @ApiModelProperty(notes = "단면도 층 표시 y 축 좌표")
  private Integer crossSectionGridY;

  private String crossSectionFileName;
  @ApiModelProperty(notes = "단면도 원본 파일명")
  private String crossSectionFileNameOrg;
  @ApiModelProperty(notes = "단면도 저장 위치(상대경로)")
  private String crossSectionFilePath;
  @ApiModelProperty(notes = "단면도 저장소. 0: local Storage ")
  private Integer crossSectionFileLocation;

  @ApiModelProperty(notes = "근로자 수")
  private Integer workerCount;

  public String getImageUrl(){
    return getViewFloorFileDownloadUrl();
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

}
