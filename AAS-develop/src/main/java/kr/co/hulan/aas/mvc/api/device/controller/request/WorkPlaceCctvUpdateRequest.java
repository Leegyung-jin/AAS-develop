package kr.co.hulan.aas.mvc.api.device.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorkPlaceCctvUpdateRequest", description="현장 CCTV 수정 요청")
public class WorkPlaceCctvUpdateRequest {

  @NotBlank(message = "CCTV 명은 필수입니다.")
  @ApiModelProperty(notes = "CCTV 명", required = true)
  private String cctvName;
  @NotBlank(message = "CCTV URL은 필수입니다.")
  @ApiModelProperty(notes = "CCTV URL", required = true)
  private String cctvUrl;
  @ApiModelProperty(notes = "설명")
  private String description;

  @NotBlank(message = "현장은 필수입니다.")
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @NotNull(message = "상태는 필수입니다.")
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용", required = true)
  private Integer status;

  @NotNull(message = "PTZ 사용여부는 필수입니다.")
  @ApiModelProperty(notes = "CCTV PTZ 사용여부. 0: 미사용, 1: 사용", required = true)
  private Integer ptzStatus;

  @ApiModelProperty(notes = "IntelliVix 채널 아이디")
  private String gid;
}
