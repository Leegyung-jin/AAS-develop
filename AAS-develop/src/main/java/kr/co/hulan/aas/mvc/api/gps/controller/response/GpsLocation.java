package kr.co.hulan.aas.mvc.api.gps.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="GpsLocation", description="Geo-Fence 좌표 정보")
@AllArgsConstructor
@NoArgsConstructor
public class GpsLocation {

  @ApiModelProperty(notes = "경도")
  private Double lat;

  @ApiModelProperty(notes = "위도")
  private Double lng;
}
