package kr.co.hulan.aas.mvc.api.hicc.vo;

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
@ApiModel(value="AttendanceDayStatVo", description="일 출역 현황")
public class AttendanceDayStatVo {

  @ApiModelProperty(notes = "출근일 ( yyyyMMdd ) ")
  private String day;
  @ApiModelProperty(notes = "출역인원 수")
  private Integer totalWorkerCount;

}
