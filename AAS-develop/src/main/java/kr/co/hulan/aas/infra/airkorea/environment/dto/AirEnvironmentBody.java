package kr.co.hulan.aas.infra.airkorea.environment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AirEnvironmentBody", description="시도별 실시간 측정 정보 응답 전문 Body")
public class AirEnvironmentBody {

  @ApiModelProperty(notes = "전체수")
  private Integer totalCount;
  @ApiModelProperty(notes = "페이지 번호")
  private Integer pageNo = 1;
  @ApiModelProperty(notes = "페이지내 결과 수")
  private Integer numOfRows = 10;

  @ApiModelProperty(notes = "측정 정보 리스트")
  private List<AirEnvironmentItem> items;


  public List<AirEnvironmentItem> getItems(){
    if( items != null ){
      return items;
    }
    return Collections.emptyList();
  }

  public int getTotalCount(){
    if( totalCount != null  ){
      return totalCount;
    }
    return 0;
  }

  public boolean isContinue(){
    if( totalCount != null
        && pageNo != null
        && numOfRows != null
    ){
      int maxCount = pageNo * numOfRows;
      return maxCount < totalCount;
    }
    return false;
  }
}
