package kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import kr.co.hulan.aas.common.code.WindSpeedRangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WindSpeedRangeVo", description="풍속 구간 정보")
public class WindSpeedRangeVo {

  @ApiModelProperty(notes = "풍속 구간 관리번호")
  private Integer idx;
  @ApiModelProperty(notes = "풍속 임계치 정책 관리번호")
  private Integer policyNo;
  @ApiModelProperty(notes = "풍속 구간명")
  private String displayName;
  @ApiModelProperty(notes = "풍속구간유형. 1: 일반, 2: 경계, 3: 중지")
  private Integer rangeType;
  @ApiModelProperty(notes = "평균 풍속 최소 구간")
  private Integer speedAvgMoreThan;
  @ApiModelProperty(notes = "평균 풍속 최대 구간")
  private Integer speedAvgLessThan;
  @ApiModelProperty(notes = "최대 풍속 최소 구간")
  private Integer speedMaxMoreThan;
  @ApiModelProperty(notes = "최대 풍속 최대 구간")
  private Integer speedMaxLessThan;
  @ApiModelProperty(notes = "경고 구간 여부")
  private Integer alert;
  @ApiModelProperty(notes = "구간 상태 메세지")
  private String statusMessage;

  @ApiModelProperty(notes = "풍속구간유형명")
  public String rangeTypeName(){
    WindSpeedRangeType wtype = WindSpeedRangeType.get(rangeType);
    return wtype != null ? wtype.getName() : "";
  }

  public boolean isAppliedSpeedAvg(Double speed){
    return new BigDecimal(speedAvgMoreThan).compareTo(new BigDecimal(speed)) <= 0
        && new BigDecimal(speedAvgLessThan).compareTo(new BigDecimal(speed)) > 0;
  }

  public boolean isAppliedSpeedMax(Double speed){
    return new BigDecimal(speedMaxMoreThan).compareTo(new BigDecimal(speed)) <= 0
        && new BigDecimal(speedMaxLessThan).compareTo(new BigDecimal(speed)) > 0;
  }

}
