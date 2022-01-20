package kr.co.hulan.aas.mvc.api.monitor4_1.vo;

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
@ApiModel(value="EnterGateStatInfo", description="출입시스템 근로자 출역 정보")
public class EnterGateStatInfo {

  @ApiModelProperty(notes = "출역인원수")
  private Integer attendanceCount;

  @ApiModelProperty(notes = "잔류인원수")
  private Integer residualCount;

  @ApiModelProperty(notes = "앱미사용 인원수")
  private Integer notUseAppCount;

}
