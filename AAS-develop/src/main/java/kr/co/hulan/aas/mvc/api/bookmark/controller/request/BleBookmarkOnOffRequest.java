package kr.co.hulan.aas.mvc.api.bookmark.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="BleBookmarkOnOffRequest", description="BLE 북마크 On/Off 요청")
public class BleBookmarkOnOffRequest {

  @NotNull(message = "건물번호는 필수입니다.")
  @ApiModelProperty(notes = "건물 번호", required = true)
  private Long buildingNo;

  @NotNull(message = "층 번호는 필수입니다.")
  @ApiModelProperty(notes = "층 번호", required = true)
  private Integer floor;

  @Min(value=0 , message = "On/Off 여부가 올바르지 않습니다.")
  @Max(value=1 , message = "On/Off 여부가 올바르지 않습니다.")
  @NotNull(message = "On/Off 여부는 필수입니다.")
  @ApiModelProperty(notes = "On/Off 여부. 0: OFF, 1: ON", required = true)
  private Integer mark;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String wpId;
}
