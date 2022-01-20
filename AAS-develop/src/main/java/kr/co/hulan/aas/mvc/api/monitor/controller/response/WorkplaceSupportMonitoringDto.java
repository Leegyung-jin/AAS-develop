package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="WorkplaceSupportMonitoringDto", description="스마트 안전 모니터 지원 현장 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceSupportMonitoringDto {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명(공사명)")
  private String wpName;
  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;
  @ApiModelProperty(notes = "건설사명")
  private String ccName;

  @ApiModelProperty(notes = "기준 longitude 좌표")
  private Double gpsCenterLong;
  @ApiModelProperty(notes = "기준 latitude 좌표")
  private Double gpsCenterLat;

  @ApiModelProperty(notes = "현장 위치 시도")
  private String wpSido;
  @ApiModelProperty(notes = "현장 위치 구군")
  private String wpGugun;

  @ApiModelProperty(notes = "대표 사진: BLE 는 조감도, GPS 는 디폴트 사진. 둘다 지원할 경우 조감도 우선")
  private String viewFileDownloadUrl;

  @ApiModelProperty(notes = "BLE 지원 여부")
  private boolean supportBle;

  @ApiModelProperty(notes = "GPS 지원 여부")
  private boolean supportGps;

  @ApiModelProperty(notes = "현장 활성 Geofence 위도")
  private Double activationGeofenceLatitude;

  @ApiModelProperty(notes = "현장 활성 Geofence 경도")
  private Double activationGeofenceLongitude;

  @ApiModelProperty(notes = "기상청 격자 x 좌표")
  private Integer nx;

  @ApiModelProperty(notes = "기상청 격자 y 좌표")
  private Integer ny;

  @JsonProperty(value = "support_3d")
  @ApiModelProperty(notes = "GPS 지원 여부")
  private boolean support3d;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String viewMapFileName;
  @JsonProperty(access = Access.WRITE_ONLY)
  private String viewMapFileNameOrg;
  @JsonProperty(access = Access.WRITE_ONLY)
  private String viewMapFilePath;
  @JsonProperty(access = Access.WRITE_ONLY)
  private Integer viewMapFileLocation;

  @JsonIgnore
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

}
