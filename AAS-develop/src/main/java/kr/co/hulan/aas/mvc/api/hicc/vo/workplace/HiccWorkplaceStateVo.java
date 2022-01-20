package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceStateVo", description="현장 현황 정보")
public class HiccWorkplaceStateVo extends HiccStateIndicatorVo {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

}
