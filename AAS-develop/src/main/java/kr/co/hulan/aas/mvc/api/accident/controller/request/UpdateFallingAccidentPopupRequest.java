package kr.co.hulan.aas.mvc.api.accident.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateFallingAccidentPopupRequest", description="낙하 이벤트 경고 팝업 On/Off 요청")
public class UpdateFallingAccidentPopupRequest {

  @NotNull(message="On/Off 여부는 필수입니다.")
  @ApiModelProperty(notes = "On/Off 여부. 0:OFF, 1:ON")
  private Integer dashboardPopup;
}
