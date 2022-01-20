package kr.co.hulan.aas.mvc.api.hicc.vo;

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
@ApiModel(value="HiccSectionVo", description="공종 정보")
public class HiccSectionVo {

  @ApiModelProperty(notes = "공종 코드")
  private String sectionCd;

  @ApiModelProperty(notes = "공종명")
  private String sectionName;

  @ApiModelProperty(notes = "상위 공종 코드")
  private String parentSectionCd;

}
