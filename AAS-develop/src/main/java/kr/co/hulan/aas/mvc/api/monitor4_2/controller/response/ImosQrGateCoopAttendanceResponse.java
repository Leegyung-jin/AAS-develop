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
@ApiModel(value="ImosQrGateCoopAttendanceResponse", description="IMOS QR Reader 출입게이트 출근유형별 협력사 현황 정보 응답")
public class ImosQrGateCoopAttendanceResponse {

  @ApiModelProperty(notes = "출근자 유형. 1: 근로자-QR (QR Scan 근로자), 2: 근로자-기타 (기타 출역 근로자), 3: 관리자 (QR Scan 관리자) ")
  private Integer attendantType;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "협력사 정보 리스트")
  private List<ImosQrGateCoopStateVo> coopList;
}
