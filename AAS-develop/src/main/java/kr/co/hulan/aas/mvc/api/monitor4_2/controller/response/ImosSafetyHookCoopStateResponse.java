package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosSafetyHookCoopStateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosSafetyHookCoopStateResponse", description="IMOS 안전고리 협력사별 현황 정보 응답")
public class ImosSafetyHookCoopStateResponse {

  @ApiModelProperty(notes = "안전고리 이벤트 유형. 1: pairing, 2: 감지구역 진입/이탈, 3: 안전고리 잠금/해제")
  private Integer eventType;

  @ApiModelProperty(notes = "상태.  0: 해제(이탈), 1: 연결(진입)")
  private Integer eventStatus;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "협력사 현황 정보 리스트")
  private List<ImosSafetyHookCoopStateVo> coopStateList;

}
