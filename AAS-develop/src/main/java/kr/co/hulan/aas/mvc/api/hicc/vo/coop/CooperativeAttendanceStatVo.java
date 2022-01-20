package kr.co.hulan.aas.mvc.api.hicc.vo.coop;

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
@ApiModel(value="CooperativeAttendanceStatVo", description="협력사 출역 현황")
public class CooperativeAttendanceStatVo {

  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;
  @ApiModelProperty(notes = "공종 코드")
  private String sectionCd;
  @ApiModelProperty(notes = "공종명")
  private String sectionName;
  @ApiModelProperty(notes = "현장 수")
  private Integer workplaceCount;
  @ApiModelProperty(notes = "출력인원 수")
  private Integer totalWorkerCount;
  @ApiModelProperty(notes = "작업인원 수")
  private Integer activeWorkerCount;
}
