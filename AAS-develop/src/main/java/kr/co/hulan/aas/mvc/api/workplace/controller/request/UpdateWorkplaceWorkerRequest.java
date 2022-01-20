package kr.co.hulan.aas.mvc.api.workplace.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateWorkplaceWorkerRequest", description="근로자 현장 편입 수정 요청")
public class UpdateWorkplaceWorkerRequest {

    @NotBlank
    @ApiModelProperty(notes = "근로자 현장 편입 아이디", required = true)
    private String wpwId;

    @NotBlank
    @ApiModelProperty(notes = "협력사 아이디", required = true)
    private String coopMbId;
    
    @ApiModelProperty(notes = "하위 공정(공정B)", required = true)
    private String workSectionB;

    @NotNull
    @ApiModelProperty(notes = "퇴출 여부. 0 : NO, 1 : YES", required = true)
    private Integer wpwOut;

    @ApiModelProperty(notes = "퇴출 사유")
    private String wpwOutMemo;

    @NotNull
    @ApiModelProperty(notes = "혈압. 0 : 정상, 1 : 주의, 2 : 고혈압", required = true)
    private Integer wpwBp;

    @NotNull
    @ApiModelProperty(notes = "수술경험(최근1년이내). 0 : 없음, 1 : 있음", required = true)
    private Integer wpwOper;

    @NotNull
    @ApiModelProperty(notes = "당뇨. 0 : 없음, 1 : 있음", required = true)
    private Integer wpwDis1;

    @NotNull
    @ApiModelProperty(notes = "고지혈증. 0 : 없음, 1 : 있음", required = true)
    private Integer wpwDis2;

    @NotNull
    @ApiModelProperty(notes = "심근경색. 0 : 없음, 1 : 있음", required = true)
    private Integer wpwDis3;

    @NotNull
    @ApiModelProperty(notes = "뇌출혈. 0 : 없음, 1 : 있음", required = true)
    private Integer wpwDis4;
}
