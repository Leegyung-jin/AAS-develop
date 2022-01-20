package kr.co.hulan.aas.mvc.api.tracker.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CollectTrackerRequest", description="트랙커 회수 요청")
public class CollectTrackerRequest {
  @NotEmpty
  @ApiModelProperty(notes = "트랙커 아이디", required = true)
  private String trackerId;

  @NotEmpty
  @ApiModelProperty(notes = "근로자 아이디", required = true)
  private String mbId;

}
