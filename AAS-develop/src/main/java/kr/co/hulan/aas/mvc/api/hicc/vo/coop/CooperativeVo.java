package kr.co.hulan.aas.mvc.api.hicc.vo.coop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CooperativeVo", description="협력사 정보")
public class CooperativeVo {

  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;

}
