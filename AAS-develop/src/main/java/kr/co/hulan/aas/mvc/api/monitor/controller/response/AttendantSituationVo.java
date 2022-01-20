package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AttendantSituationVo", description="모니터링 협력사 출력인원수 정보")
public class AttendantSituationVo {
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
    @ApiModelProperty(notes = "출력인원수")
    private Long workerTodayCount;
    @ApiModelProperty(notes = "현재 작업자수")
    private Long workerCurrentCount;


}
