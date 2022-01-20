package kr.co.hulan.aas.mvc.api.building.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorBuildingLocationResponse", description="빌딩 층별 센서 위치 정보  응답")
public class ListSensorBuildingLocationByFloorResponse {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;


  @ApiModelProperty(notes = "층도면 파일명")
  private String viewFloorFileName;

  @ApiModelProperty(notes = "층도면 원본 파일명")
  private String viewFloorFileNameOrg;

  @ApiModelProperty(notes = "층도면 저장 위치(상대경로)")
  private String viewFloorFilePath;

  @ApiModelProperty(notes = "층도면 저장소. 0: local Storage ")
  private Integer viewFloorFileLocation;


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

  @ApiModelProperty(notes = "센서 위치 정보 리스트")
  private List<SensorBuildingLocationDto> sensorList;


}
