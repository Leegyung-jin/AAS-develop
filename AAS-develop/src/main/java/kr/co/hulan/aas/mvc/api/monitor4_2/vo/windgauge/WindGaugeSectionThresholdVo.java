package kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge;

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
@ApiModel(value="WindGaugeSectionThresholdVo", description="풍속계 임계치 정보")
public class WindGaugeSectionThresholdVo {

  @ApiModelProperty(notes = "풍속계 정책 번호")
  private Integer policyNo;

  @ApiModelProperty(notes = "경계 풍속 임계치")
  private Integer cautionSpeed;

  @ApiModelProperty(notes = "중지 풍속 임계치")
  private Integer stopSpeed;

}
