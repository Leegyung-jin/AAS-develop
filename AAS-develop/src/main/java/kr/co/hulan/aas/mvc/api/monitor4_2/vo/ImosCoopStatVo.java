package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosCoopStatVo", description="IMOS 협력사 근로자 수 정보")
public class ImosCoopStatVo {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;
  @ApiModelProperty(notes = "협력사 공종A명")
  private String workSectionNameA;

  @ApiModelProperty(notes = "전체 수")
  private Integer totalWorkerCount;

  @ApiModelProperty(notes = "근로자수 수")
  private Integer workerCount;


}
