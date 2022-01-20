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
@ApiModel(value="UpdateTrackerRequest", description="트랙커 수정 요청")
public class UpdateTrackerRequest {

  @NotEmpty
  @ApiModelProperty(notes = "트랙커 아이디", required = true)
  private String trackerId;

  @NotEmpty
  @ApiModelProperty(notes = "트랙커 디바이스 모델명", required = true)
  private String deviceModel;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
}
