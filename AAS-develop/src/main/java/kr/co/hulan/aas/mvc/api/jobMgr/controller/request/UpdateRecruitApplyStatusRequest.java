package kr.co.hulan.aas.mvc.api.jobMgr.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/*
변경 없다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateRecruitApplyStatusRequest", description="구직 정보 상태 업데이트 요청")
 */
public class UpdateRecruitApplyStatusRequest {

    @NotNull
    @ApiModelProperty(notes = "구직 공고 아이디", required = true)
    private Integer raIdx;

    @NotEmpty
    @ApiModelProperty(notes = "구직 상태. 0 : 대기, 1 : 심사중, 2 : 현장편입 ")
    private String raStatus;

}
