package kr.co.hulan.aas.mvc.api.hicc.vo.main;

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
@ApiModel(value="HiccNationWideStateIndicatorVo", description="전국 지표 정보")
public class HiccNationWideStateIndicatorVo {

  @ApiModelProperty(notes = "출력인원수")
  private Long totalWorkerCount;

  @ApiModelProperty(notes = "작업인원수")
  private Long activeWorkerCount;

  @ApiModelProperty(notes = "위험근로자수")
  private Long dangerWorkerCount;

  @ApiModelProperty(notes = "종료인원수")
  public Long getWorkOffCount(){
    if( totalWorkerCount != null && activeWorkerCount != null ){
      return totalWorkerCount - activeWorkerCount;
    }
    return 0L;
  }
}
