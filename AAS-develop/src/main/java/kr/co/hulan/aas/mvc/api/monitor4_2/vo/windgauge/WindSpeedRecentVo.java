package kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.EnableCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WindSpeedRecentVo", description="풍속계 최근 수치 정보")
public class WindSpeedRecentVo extends WindSpeedFigureVo{

  private Integer dashboardPopup;

  @ApiModelProperty(notes = "구간 유형. 1: 일반, 2: 경계, 3: 중지")
  private Integer rangeType;
  @ApiModelProperty(notes = "위험 경고 여부")
  private boolean emergency;
  @ApiModelProperty(notes = "위험 경고명")
  private String emergencyName;
  @ApiModelProperty(notes = "풍속 안내문구")
  private String windSpeedDesc;

  public void applyWindSpeedRange(WindSpeedRangeChecker checker){
    if( checker != null ){
      WindSpeedRangeVo range = checker.getAppliedRange( getSpeedAvg(), getSpeedMax());
      if( range != null ){
        this.rangeType = range.getRangeType();
        this.emergency = EnableCode.get(range.getAlert()) == EnableCode.ENABLED;
        this.emergencyName = range.getDisplayName();
        this.windSpeedDesc = range.getStatusMessage();
      }
    }
  }

}
