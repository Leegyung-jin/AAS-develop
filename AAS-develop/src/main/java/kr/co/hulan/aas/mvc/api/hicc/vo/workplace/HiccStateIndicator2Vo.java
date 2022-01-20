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
@ApiModel(value="HiccStateIndicator2Vo", description="현황 지표 2.0")
public class HiccStateIndicator2Vo {

  @ApiModelProperty(notes = "관리자 출력인원수")
  private Integer totalManagerCount = 0;

  @ApiModelProperty(notes = "근로자 출력인원수")
  private Integer totalWorkerCount = 0;

  @ApiModelProperty(notes = "근로자 작업인원수")
  private Integer activeWorkerCount = 0;

  @ApiModelProperty(notes = "협력업체수")
  private Integer coopCount = 0;

  @ApiModelProperty(notes = "위험근로자수(고위험수)")
  private Integer dangerWorkerCount = 0;

  @ApiModelProperty(notes = "장비 및 차량수(운영장비수)")
  private Integer equipmentCount = 0;

  @ApiModelProperty(notes = "위험알림수")
  private Integer dangerMsgCount = 0;
}
