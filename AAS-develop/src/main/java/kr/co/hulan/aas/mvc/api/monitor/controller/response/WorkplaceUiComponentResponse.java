package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.UiComponentDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceUiComponentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorkplaceUiComponentResponse", description="현장 UI 컴포넌트 정보 응답")
public class WorkplaceUiComponentResponse {

  private WorkPlaceUiComponentDto uiComponent;

  private List<UiComponentDto> supportedUiComponents;
}
