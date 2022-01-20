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
@ApiModel(value="HiccStateIndicatorVo", description="현황 지표")
public class HiccStateIndicatorVo {

  @ApiModelProperty(notes = "앱 사용자수")
  private Integer useAppWorkerCount = 0;

  @ApiModelProperty(notes = "출력인원수")
  private Integer totalWorkerCount = 0;

  @ApiModelProperty(notes = "작업인원수")
  private Integer activeWorkerCount = 0;

  @ApiModelProperty(notes = "위험근로자수")
  private Integer dangerWorkerCount = 0;

  @ApiModelProperty(notes = "AP센서수")
  private Integer sensorCount = 0;

  @ApiModelProperty(notes = "위험알림수")
  private Integer dangerMsgCount = 0;

  @ApiModelProperty(notes = "앱 미사용자수")
  public Integer getNotUseAppWorkerCount(){
    if( totalWorkerCount != null && useAppWorkerCount != null ){
      return totalWorkerCount - useAppWorkerCount;
    }
    return 0;
  }

  public void resetNullData(){
    if( useAppWorkerCount == null ){
      useAppWorkerCount = 0;
    }
    if( useAppWorkerCount == null ){
      useAppWorkerCount = 0;
    }
    if( totalWorkerCount == null ){
      totalWorkerCount = 0;
    }
    if( activeWorkerCount == null ){
      activeWorkerCount = 0;
    }
    if( dangerWorkerCount == null ){
      dangerWorkerCount = 0;
    }
    if( sensorCount == null ){
      sensorCount = 0;
    }
    if( dangerMsgCount == null ){
      dangerMsgCount = 0;
    }
  }
}
