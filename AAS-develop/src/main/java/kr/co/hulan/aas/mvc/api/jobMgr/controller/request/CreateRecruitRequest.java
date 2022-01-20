package kr.co.hulan.aas.mvc.api.jobMgr.controller.request;

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
@ApiModel(value="CreateRecruitRequest", description="구인 정보 생성 요청")
public class CreateRecruitRequest {

    @NotEmpty
    @ApiModelProperty(notes = "공고명", required = true)
    private String rcTitle;

    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotEmpty
    @ApiModelProperty(notes = "협력사 아이디", required = true)
    private String coopMbId;

    @NotEmpty
    @ApiModelProperty(notes = "공종A", required = true)
    private String workSectionA;

    @NotNull
    @ApiModelProperty(notes = "공종B")
    private String workSectionB;

    @NotEmpty
    @ApiModelProperty(notes = "임금", required = true)
    private String rcPayAmount;
    @NotEmpty
    @ApiModelProperty(notes = "임금 단위", required = true)
    private String rcPayUnit;

    @NotEmpty
    @ApiModelProperty(notes = "채용담당전화번호", required = true)
    private String rcTel;

    @NotEmpty
    @ApiModelProperty(notes = "내용", required = true)
    private String rcContent;
}
