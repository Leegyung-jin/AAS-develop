package kr.co.hulan.aas.mvc.api.safetySituation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.WorkerWorkPrintStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value="WorkPrintDto", description="출력일보 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkPrintDto {

    @ApiModelProperty(notes = "출력일보 아이디")
    private Integer wwpIdx;

    @ApiModelProperty(notes = "건설사명")
    private String ccName;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "현장명")
    private String wpName;

    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;

    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;

    @ApiModelProperty(notes = "상태코드")
    private Integer wwpStatus;

    @ApiModelProperty(notes = "작성일", required = true)
    private java.util.Date wwpDate;

    @ApiModelProperty(notes = "최종 작성일", required = true)
    private java.util.Date wwpUpdatetime;

    @ApiModelProperty(notes = "직종", required = true)
    private List<String> wwpJob;

    @ApiModelProperty(notes = "성명", required = true)
    private List<String> workerMbName;

    @ApiModelProperty(notes = "작업 내용", required = true)
    private List<String> wwpWork;

    @ApiModelProperty(notes = "비고", required = true)
    private List<String> wwpMemo;

    @ApiModelProperty(notes = "상태명")
    public String getWwpStatusName(){
        if( wwpStatus != null ){
            WorkerWorkPrintStatus status = WorkerWorkPrintStatus.get(wwpStatus);
            if( status != null ){
                return status.getName();
            }
        }
        return "";
    }

    @ApiModelProperty(notes = "인원")
    public Integer getWorkerCount(){
        if( workerMbName != null ){
            return workerMbName.size();
        }
        return 0;
    }

}
