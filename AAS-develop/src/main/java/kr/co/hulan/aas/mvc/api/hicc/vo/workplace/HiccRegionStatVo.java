package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

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
@ApiModel(value="HiccRegionStatVo", description="권역별 통계")
public class HiccRegionStatVo {

  @ApiModelProperty(notes = "권역 번호")
  private Long hrgNo;

  @ApiModelProperty(notes = "권역명")
  private String hrgName;

  @ApiModelProperty(notes = "현장수")
  private Integer totalWorkplaceCount;

  @ApiModelProperty(notes = "협력사 수")
  private Integer totalCoopCount;

  @ApiModelProperty(notes = "출역근로자 수")
  private Integer totalWorkerCount;

}
