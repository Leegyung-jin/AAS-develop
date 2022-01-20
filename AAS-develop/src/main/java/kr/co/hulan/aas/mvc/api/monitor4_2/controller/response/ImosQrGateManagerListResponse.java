package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosAttendantManagerVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosQrGateManagerListResponse", description="IMOS QR Reader 출입게이트 출근매니저 리스트 응답")
public class ImosQrGateManagerListResponse {


  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "출근자 정보 리스트")
  private List<ImosAttendantManagerVo> attenendantList;
}
