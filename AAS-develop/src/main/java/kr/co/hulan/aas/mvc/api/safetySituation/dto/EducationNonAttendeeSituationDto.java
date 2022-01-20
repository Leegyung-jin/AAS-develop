package kr.co.hulan.aas.mvc.api.safetySituation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@ApiModel(value="EducationNonAttendeeSituationDto", description="안전교육미참석자 현황 정보")
@AllArgsConstructor
@NoArgsConstructor
public class EducationNonAttendeeSituationDto {

    @ApiModelProperty(notes = "협력사 현장 편입 아이디")
    private String wpcId;
    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    @ApiModelProperty(notes = "현장명(공사명)")
    private String wpName;
    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;
    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;


    @ApiModelProperty(notes = "지정일")
    private java.util.Date targetDate;

    @ApiModelProperty(notes = "당일 노동자 수")
    private Long workerTodayCount;

    @ApiModelProperty(notes = "당일 안전교육 참석자수")
    private Long workerSafetyEducationCount;

    @ApiModelProperty(notes = "당일 안전교육 미참석자수")
    public Long getWorkerNonSafetyEducationCount(){
        if( workerTodayCount == null || workerSafetyEducationCount == null ){
            return null;
        }
        return Math.max(workerTodayCount - workerSafetyEducationCount, 0);

    }
}
