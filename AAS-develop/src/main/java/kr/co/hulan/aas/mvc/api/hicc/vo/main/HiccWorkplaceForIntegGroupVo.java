package kr.co.hulan.aas.mvc.api.hicc.vo.main;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceForIntegGroupVo", description="권역별 현장 요약 정보")
public class HiccWorkplaceForIntegGroupVo {

  @ApiModelProperty(notes = "권역 넘버")
  private Long hrgNo;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "출역인원수")
  private Long totalWorkerCount;

  @ApiModelProperty(notes = "작업인원수")
  private Long activeWorkerCount;

  @ApiModelProperty(notes = "위험알림수")
  private Long dangerMsgCount;


  @ApiModelProperty(notes = "착공일")
  private java.util.Date wpStartDate;

  @ApiModelProperty(notes = "준공(예정)일")
  private Date wpEndDate;

  @ApiModelProperty(notes = "공사 규모")
  private String  constructScale;

  @ApiModelProperty(notes = "현장 날씨 정보")
  private HiccWeatherInfoVo weather;


  @ApiModelProperty(notes = "공사진척도(%)")
  public Long getElapsedWork(){
    LocalDate now = LocalDate.now();
    if( wpStartDate != null && wpEndDate != null ){
      LocalDate startLocalDate = Instant.ofEpochMilli(wpStartDate.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
      long elapsedDays = ChronoUnit.DAYS.between(startLocalDate, now);
      if( elapsedDays > 0 ){
        LocalDate endLocalDate = Instant.ofEpochMilli(wpEndDate.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        long totalDays = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        if( totalDays <= 0 ){
          return 100L;
        }
        return Math.min( elapsedDays * 100 /totalDays, 100L);
      }
    }
    return 0L;
  }

}
