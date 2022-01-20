package kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.common.code.EnableCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WindGaugeStatusVo", description="풍속계 상태 정보")
public class WindGaugeStatusVo {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "Device 넘버")
  private Integer idx;
  @ApiModelProperty(notes = "Mac주소")
  private String macAddress;
  @ApiModelProperty(notes = "Device명")
  private String displayName;

  @ApiModelProperty(notes = "풍속계 임계치 정보")
  private WindGaugeSectionThresholdVo threshold;

  @ApiModelProperty(notes = "풍속계 현재 수치")
  private WindSpeedRecentVo recent;

  @ApiModelProperty(notes = "풍속계 수치 리스트")
  private List<WindSpeedFigureVo> history;

  @ApiModelProperty(notes = "위험 경고 여부")
  public boolean isEmergency(){
    if( recent != null ){
      return recent.isEmergency();
    }
    return false;
  }

  @ApiModelProperty(notes = "위험 경고명")
  public String getEmergencyName(){
    if( recent != null ){
      return recent.getEmergencyName();
    }
    return "";
  }

  public void applyWindSpeedRange(WindSpeedRangeChecker checker){
    if( recent != null ){
      recent.applyWindSpeedRange( checker );
    }
  }

}
