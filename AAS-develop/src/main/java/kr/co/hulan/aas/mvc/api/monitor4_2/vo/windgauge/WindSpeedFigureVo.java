package kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WindSpeedFigureVo", description="풍속계 수치 정보")
public class WindSpeedFigureVo {

  @ApiModelProperty(notes = "디바이스 관리번호")
  private Integer idx;
  @ApiModelProperty(notes = "Mac주소")
  private String macAddress;
  @ApiModelProperty(notes = "측정시간")
  private Date measureTime;
  @ApiModelProperty(notes = "Device명")
  private String displayName;
  @ApiModelProperty(notes = "평균 풍속")
  private Double speedAvg;
  @ApiModelProperty(notes = "최대 풍속")
  private Double speedMax;


}
