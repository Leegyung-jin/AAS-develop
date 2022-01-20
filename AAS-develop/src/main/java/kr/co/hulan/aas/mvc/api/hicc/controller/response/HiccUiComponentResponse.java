package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccMemberUiComponentVo;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccUiComponentResponse", description="통합 관제 컴포넌트 정보 응답")
public class HiccUiComponentResponse {

  @ApiModelProperty(notes = "현재 지정된 컴포넌트 정보")
  private HiccMemberUiComponentVo uiComponent;

  @ApiModelProperty(notes = "지정가능한 컴포넌트 정보 리스트")
  private List<HulanUiComponentVo> supportedUiComponents;
}
