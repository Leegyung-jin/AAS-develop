package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.gps.controller.response.GpsLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkGeofenceGroupDto", description="GPS fence Group 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkGeofenceGroupDto {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "현장 fence 그룹 순번")
  private Integer wpSeq;
  @ApiModelProperty(notes = "현장 fence Group 명")
  private String fenceName;
  @ApiModelProperty(notes = "중심 latitude")
  private Double centerLatitude;
  @ApiModelProperty(notes = "중심 longitude")
  private Double centerLongitude;

  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자")
  private String updater;

  @ApiModelProperty(notes = "fence 위치 정보")
  private List<WorkGeofenceInfoDto> fenceLocations;

}
