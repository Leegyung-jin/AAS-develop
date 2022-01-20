package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccRegionStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccStateIndicator2Vo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccStateIndicatorVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccStateIndicatorResponse", description="전국현황 응답")
public class HiccStateIndicatorResponse {

  @ApiModelProperty(notes = "전국 현황 정보")
  private HiccStateIndicator2Vo stateIndicator;

  @ApiModelProperty(notes = "권역별 현황 리스트")
  private List<HiccRegionStatVo> regionStatList;
}
