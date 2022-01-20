package kr.co.hulan.aas.infra.airkorea.station.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.List;
import kr.co.hulan.aas.infra.airkorea.environment.dto.AirEnvironmentItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NearStationBody", description="근접측정소 목록 조회 Body")
public class NearStationBody {

  @ApiModelProperty(notes = "전체수")
  private Integer totalCount;
  @ApiModelProperty(notes = "페이지 번호")
  private Integer pageNo = 1;
  @ApiModelProperty(notes = "페이지내 결과 수")
  private Integer numOfRows = 10;

  @ApiModelProperty(notes = "근접측정소 리스트")
  private List<NearStationItem> items;


  public List<NearStationItem> getItems(){
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
}
