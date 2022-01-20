package kr.co.hulan.aas.mvc.api.safetySituation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="EducationNonAttendeeSituationDto", description="안전교육 (미)참석자 정보")
@AllArgsConstructor
@NoArgsConstructor
public class EducationNonAttendeeDto {

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    @ApiModelProperty(notes = "현장명")
    private String wpName;
    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;
    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;
    @ApiModelProperty(notes = "근로자 아이디(전화번호)")
    private String workerMbId;
    @ApiModelProperty(notes = "성명")
    private String workerMbName;

    @ApiModelProperty(notes = "지정일")
    private java.util.Date targetDate;

    @ApiModelProperty(notes = "안전교육 참석 시간")
    private java.util.Date educationAttendDate;

    @ApiModelProperty(notes = "안전교육 참석 여부")
    private String attendStatus = "";
}
