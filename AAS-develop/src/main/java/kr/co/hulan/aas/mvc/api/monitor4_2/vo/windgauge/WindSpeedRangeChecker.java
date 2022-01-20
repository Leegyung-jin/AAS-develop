package kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge;

import io.swagger.annotations.ApiModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.EnableCode;

@ApiModel(value="WindSpeedRangeVo", description="풍속 구간 정보")
public class WindSpeedRangeChecker implements Comparator<WindSpeedRangeVo> {

  public List<WindSpeedRangeVo> rangeList;

  public WindSpeedRangeChecker(List<WindSpeedRangeVo> rangeList){
    if( rangeList != null ){
      Collections.sort(rangeList, this);
      this.rangeList = rangeList;
    }
    else {
      this.rangeList = Collections.emptyList();
    }
  }

  public List<WindSpeedRangeVo> getAlertRangeList(){
    return rangeList.stream().filter( range -> EnableCode.get(range.getAlert()) == EnableCode.ENABLED ).collect(
        Collectors.toList());
  }

  public WindSpeedRangeVo getAppliedRange(Double speedAvg, Double speedMax ){
    if( speedAvg != null && speedMax != null ){
      for(WindSpeedRangeVo range : rangeList ){
        if( range.isAppliedSpeedAvg( speedAvg ) || range.isAppliedSpeedMax(speedMax)){
          return range;
        }
      }
    }
    return null;
  }

  @Override
  public int compare(WindSpeedRangeVo o1, WindSpeedRangeVo o2){
    if( o1 == null ) { return -1; }
    if( o2 == null ) { return 1; }

    if( o1.getSpeedMaxMoreThan() == null ) { return -1; }
    if( o2.getSpeedMaxMoreThan() == null ) { return 1; }

    return o1.getSpeedMaxMoreThan().compareTo(o2.getSpeedMaxMoreThan());
  }

}
