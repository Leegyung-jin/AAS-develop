package kr.co.hulan.aas.mvc.api.gps.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="GeoFenceInfoResponse", description="Geo-Fence 좌표 리스트 응답")
public class GeoFenceInfoResponse {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "GPS Center 정보")
  private GpsLocation gpsCenter;

  @ApiModelProperty(notes = "Geo-Fence 좌표 리스트. 0: Main, 그외 Sub")
  private Map<Integer, List<GpsLocation>> geofenceList;
}
