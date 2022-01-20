package kr.co.hulan.aas.mvc.api.component.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.UiComponentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListUiComponentResponse", description="현장 모니터링 UI 컴포넌트 검색 응답")
public class ListUiComponentResponse {

  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "결과 리스트")
  private List<UiComponentDto> list;

}
