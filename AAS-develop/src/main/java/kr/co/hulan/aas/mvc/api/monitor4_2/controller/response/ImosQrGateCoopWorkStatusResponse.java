package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosQrGateCoopStateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosQrGateCoopWorkStatusResponse", description="IMOS QR Reader 출입게이트 협력사별 근로자 작업현황 정보 응답")
public class ImosQrGateCoopWorkStatusResponse {

  @ApiModelProperty(notes = "현재 작업여부. 0: 종료, 1: 작업")
  private Integer workingStatus;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "협력사 정보 리스트")
  private List<ImosQrGateCoopStateVo> coopList;
}
