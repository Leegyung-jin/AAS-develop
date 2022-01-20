package kr.co.hulan.aas.mvc.api.tracker.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.TrackerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListTrackerResponse", description="트랙커 리스트 응답")
public class ListTrackerResponse {

  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "트랙커 리스트")
  private List<TrackerDto> list;

}
