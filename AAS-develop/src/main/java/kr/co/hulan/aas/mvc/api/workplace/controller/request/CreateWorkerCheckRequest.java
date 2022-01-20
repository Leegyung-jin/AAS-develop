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
@ApiModel(value="CreateWorkerCheckRequest", description="고위험/주요근로자 생성 요청")
public class CreateWorkerCheckRequest {

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
    @ApiModelProperty(notes = ". 고위험/주요근로자 구분. 1:주요근로자, 2:고위험.")
    private Integer wcType;

    @NotNull
    @ApiModelProperty(notes = "등록사유")
    private String wcMemo;


}
