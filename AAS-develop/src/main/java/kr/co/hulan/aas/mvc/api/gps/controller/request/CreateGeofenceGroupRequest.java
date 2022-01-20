package kr.co.hulan.aas.mvc.api.gps.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.hulan.aas.mvc.model.dto.WorkGeofenceInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateGeofenceGroupRequest", description="Fence 생성 요청")
public class CreateGeofenceGroupRequest {

  @NotBlank(message = "현장 아이디는 필수입니다.")
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @NotNull(message = "중심 좌표는 필수입니다.")
  @ApiModelProperty(notes = "중심 latitude", required = true)
  private Double centerLatitude;

  @NotNull(message = "중심 좌표는 필수입니다.")
  @ApiModelProperty(notes = "중심 longitude", required = true)
  private Double centerLongitude;

  @NotBlank(message = "Fence명은 필수입니다.")
  @ApiModelProperty(notes = "Fence명", required = true)
  private String fenceName;

  @Size(min = 3, message = "최소 3개의 좌표는 있어야 합니다.")
  @NotNull(message = "fence 좌표 리스트는 필수입니다.")
  @ApiModelProperty(notes = "fence 좌표 리스트", required = true)
  private List<@Valid WorkGeofenceInfoDto> fenceLocations;


}
