package kr.co.hulan.aas.mvc.api.safetySituation.controller.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="CreateWorkerWorkPrintRequest", description="출력일보 등록 요청")
public class CreateWorkerWorkPrintRequest {

    @NotEmpty(message = "현장 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "현장 아이디", required = true)
    @JsonProperty("wp_id")
    private String wpId;

    @NotEmpty(message = "협력사 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "협력사 아이디", required = true)
    @JsonProperty("coop_mb_id")
    private String coopMbId;

    @NotNull
    @ApiModelProperty(notes = "작성일", required = true)
    @JsonProperty("wwp_date")
    private Date wwpDate;


    @Size(min=1)
    @ApiModelProperty(notes = "직종", required = true)
    @JsonProperty("wwp_job")
    private List<String> wwpJob;

    @Size(min=1)
    @ApiModelProperty(notes = "성명", required = true)
    @JsonProperty("worker_mb_name")
    private List<String> workerMbName;

    @Size(min=1)
    @ApiModelProperty(notes = "작업 내용", required = true)
    @JsonProperty("wwp_work")
    private List<String> wwpWork;

    @Size(min=1)
    @ApiModelProperty(notes = "비고", required = true)
    @JsonProperty("wwp_memo")
    private List<String> wwpMemo;

}
