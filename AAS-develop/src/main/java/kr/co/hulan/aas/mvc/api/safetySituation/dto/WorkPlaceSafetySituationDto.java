package kr.co.hulan.aas.mvc.api.safetySituation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkPlaceSafetySituationDto", description="현장별 안전관리 현황 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceSafetySituationDto {

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    @ApiModelProperty(notes = "현장명(공사명)")
    private String wpName;
    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;

    @ApiModelProperty(notes = "당일 노동자 수")
    private Long workerTodayCount;
    @ApiModelProperty(notes = "당월 노동자 수")
    private Long workerMonthCount;
    @ApiModelProperty(notes = "전체 노동자 수")
    private Long workerTotalCount;
}
