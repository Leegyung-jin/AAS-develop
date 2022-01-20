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
@ApiModel(value="SectionStatVo", description="공종별 협력사 수")
public class SectionStatVo {

  @ApiModelProperty(notes = "공종 코드")
  private String sectionCd;

  @ApiModelProperty(notes = "공종명")
  private String sectionName;

  @ApiModelProperty(notes = "협력사 수")
  private Integer coopCount;
}
