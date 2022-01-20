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
@ApiModel(value="UpdateWorkerWarnRequest", description="근로자 경고 수정 요청")
public class UpdateWorkerWarnRequest {

    @NotNull
    @ApiModelProperty(notes = "근로자 경고 아이디", required = true)
    private Integer wwIdx;

    @NotNull
    @ApiModelProperty(notes = "경고사유")
    private String wwContent;
}
