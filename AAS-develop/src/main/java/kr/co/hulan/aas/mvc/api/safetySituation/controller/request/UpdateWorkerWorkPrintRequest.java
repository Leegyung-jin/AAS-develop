package kr.co.hulan.aas.mvc.api.safetySituation.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UpdateWorkerWorkPrintRequest", description="출력일보 수정 요청")
public class UpdateWorkerWorkPrintRequest {

    @NotNull(message = "출력일보 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "출력일보 아이디", required = true)
    @JsonProperty("wwp_idx")
    private Integer wwpIdx;

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
