package kr.co.hulan.aas.mvc.api.region.vo;

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
@ApiModel(value="RegionSidoVo", description="시도 정보")
public class RegionSidoVo {

  @ApiModelProperty(notes = "시도 코드")
  private String sidoCd;
  @ApiModelProperty(notes = "시도명")
  private String sidoNm;
  @ApiModelProperty(notes = "시도명(2자리 짧은명)")
  private String sidoShortNm;
  @ApiModelProperty(notes = "순서 (오름차순)")
  private Integer sidoOrd;

  @ApiModelProperty(notes = "기상청 위경도 격자X")
  private Integer nx;

  @ApiModelProperty(notes = "기상청 위경도 격자Y")
  private Integer ny;
}
