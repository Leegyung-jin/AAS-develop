package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CurrentWorkStatusSummary;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosCommuteTypeStatVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosWorkStatusResponse", description="[4.2] 전체 출역 현황 정보")
public class ImosWorkStatusResponse {

  @ApiModelProperty(notes = "출근유형별 인원 리스트")
  private List<ImosCommuteTypeStatVo> commuteTypeList;

  @ApiModelProperty(notes = "현재 작업인원 현황 정보")
  private CurrentWorkStatusSummary currentWorkStatus;
}
