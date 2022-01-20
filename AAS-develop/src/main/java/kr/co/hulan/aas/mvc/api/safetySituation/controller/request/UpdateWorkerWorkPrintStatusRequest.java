package kr.co.hulan.aas.mvc.api.safetySituation.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UpdateWorkerWorkPrintStatusRequest", description="출력일보 상태 수정 요청")
public class UpdateWorkerWorkPrintStatusRequest {

    @NotEmpty(message = "출력일보 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "출력일보 아이디", required = true)
    private Integer wwpIdx;

    @NotEmpty
    @ApiModelProperty(notes = "출력일보 상태", required = true)
    private Integer wwpStatus;
}
