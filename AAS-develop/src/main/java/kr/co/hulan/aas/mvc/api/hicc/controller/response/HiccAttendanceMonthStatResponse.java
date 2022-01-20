package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.AttendanceMonthStatVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccAttendanceMonthStatResponse", description="월간 출역 현황 응답")
public class HiccAttendanceMonthStatResponse {

  @ApiModelProperty(notes = "월별 출역 현황 리스트")
  private List<AttendanceMonthStatVo> list;

  @ApiModelProperty(notes = "리스트 수")
  public long getCount(){
    if( list != null && list.size() > 0 ){
      return list.size();
    }
    return 0L;
  }
}
