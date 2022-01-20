package kr.co.hulan.aas.mvc.api.gps.controller.response;

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
@ApiModel(value="ListGeofenceGroupResponse", description="Geofence 검색 응답")
public class ListGeofenceGroupResponse {

  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "결과 리스트")
  private List<WorkGeofenceGroupDto> list;
}
