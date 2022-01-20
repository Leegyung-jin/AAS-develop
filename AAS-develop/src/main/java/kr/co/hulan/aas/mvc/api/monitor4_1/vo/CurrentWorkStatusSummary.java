package kr.co.hulan.aas.mvc.api.monitor4_1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CurrentWorkStatusSummary", description="현재 작업 인원 현황 정보")
public class CurrentWorkStatusSummary {

  @ApiModelProperty(notes = "총 출력인원", hidden = true)
  private Long totalWorkerCount;

  @ApiModelProperty(notes = "작업인원")
  private Long currentWorkerCount;

  @ApiModelProperty(notes = "종료인원")
  public Long getWorkOffCount(){
    if( totalWorkerCount != null && currentWorkerCount != null ){
      return totalWorkerCount - currentWorkerCount;
    }
    return 0L;
  }
}
