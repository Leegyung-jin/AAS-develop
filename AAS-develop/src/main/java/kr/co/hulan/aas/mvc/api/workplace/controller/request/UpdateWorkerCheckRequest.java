package kr.co.hulan.aas.mvc.api.workplace.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateWorkerCheckRequest", description="고위험/주요근로자 수정 요청")
public class UpdateWorkerCheckRequest {

    @NotNull
    @ApiModelProperty(notes = "고위험/주요근로자 아이디", required = true)
    private Integer wcIdx;

    @NotNull
    @ApiModelProperty(notes = "등록사유")
    private String wcMemo;
}
