package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccIntegGroupVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccNationWideZoneResponse", description="권역별 현장 리스트 응답")
public class HiccNationWideZoneResponse {

  @ApiModelProperty(notes = "권역 리스트")
  private List<HiccIntegGroupVo> zoneList;

}
