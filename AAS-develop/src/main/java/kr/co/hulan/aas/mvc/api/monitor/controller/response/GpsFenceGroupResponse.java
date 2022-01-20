package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.WorkGeofenceGroupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="GpsFenceGroupResponse", description="Gps-Fence 그룹 리스트 응답")
public class GpsFenceGroupResponse {

  @ApiModelProperty(notes = "Fence 전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "GPS 현장 latitude")
  private Double gpsCenterLat;
  @ApiModelProperty(notes = "GPS 현장 longitude")
  private Double gpsCenterLong;
  @ApiModelProperty(notes = "Fence 리스트")
  private List<WorkGeofenceGroupDto> groupList;

}
