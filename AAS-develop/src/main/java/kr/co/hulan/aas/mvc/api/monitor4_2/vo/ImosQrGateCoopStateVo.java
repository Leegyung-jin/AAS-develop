package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

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
@ApiModel(value="ImosQrGateCoopStateVo", description="IMOS QrGate 협력사 현황 정보 응답")
public class ImosQrGateCoopStateVo {

  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;

  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;

  @ApiModelProperty(notes = "협력사 공종코드")
  private String workSectionA;

  @ApiModelProperty(notes = "협력사 공종명")
  private String workSectionNameA;

  @ApiModelProperty(notes = "근로자수")
  private Long workerCnt;
}
