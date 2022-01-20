package kr.co.hulan.aas.mvc.api.workplace.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateWorkerWarnRequest", description="근로자 경고 생성 요청")
public class CreateWorkerWarnRequest {


    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotEmpty
    @ApiModelProperty(notes = "협력사 아이디", required = true)
    private String coopMbId;

    @NotEmpty
    @ApiModelProperty(notes = "근로자 아이디(전화번호)", required = true)
    private String workerMbId;

    @NotNull
    @ApiModelProperty(notes = "경고내용")
    private String wwContent;
}
