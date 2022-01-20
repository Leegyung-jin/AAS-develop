package kr.co.hulan.aas.mvc.api.workplace.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateWorkplaceCooperativeCompanyRequest", description="협력사 현장 편입 생성 요청")
public class CreateWorkplaceCooperativeCompanyRequest {

    @NotBlank(message = "현장 아이디는 필수입니다.")
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotBlank(message = "건설사 아이디는 필수입니다.")
    @ApiModelProperty(notes = "건설사 아이디", required = true)
    private String ccId;

    @NotBlank(message = "협력사 아이디는 필수입니다.")
    @ApiModelProperty(notes = "협력사 아이디", required = true)
    private String coopMbId;

    @NotBlank(message = "공종A코드(협력사 공종)는 필수입니다.")
    @ApiModelProperty(notes = "공종A코드(협력사 공종)", required = true)
    private String workSectionA;

}
