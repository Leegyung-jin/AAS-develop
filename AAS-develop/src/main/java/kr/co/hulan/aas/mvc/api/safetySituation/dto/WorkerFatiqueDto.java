package kr.co.hulan.aas.mvc.api.safetySituation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.WorkerFatique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@ApiModel(value="WorkerFatiqueDto", description="근로자 피로도 현황 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkerFatiqueDto {

    @ApiModelProperty(notes = "날짜")
    private String sliDate;

    @ApiModelProperty(notes = "건설사명")
    private String ccName;

    @ApiModelProperty(notes = "현장명")
    private String wpName;

    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;

    @ApiModelProperty(notes = "근로자 아이디(전화번호)")
    private String workerMbId;

    @ApiModelProperty(notes = "성명")
    private String workerMbName;

    @ApiModelProperty(notes = "금일 근무시간")
    private Integer todayWorkTime;
    @ApiModelProperty(notes = "전일 근무시간")
    private Integer yesterdayWorkTime;
    @ApiModelProperty(notes = "금주 근무시간")
    private Integer weekWorkTime;

    @ApiModelProperty(notes = "피로도")
    public String getTiredStatus(){
        if( yesterdayWorkTime != null ){
            WorkerFatique fat = WorkerFatique.get(yesterdayWorkTime);
            if( fat != null ){
                return fat.getName();
            }
            else {
                return "";
            }
        }
        return WorkerFatique.NOMRAL.getName();
    }

}
