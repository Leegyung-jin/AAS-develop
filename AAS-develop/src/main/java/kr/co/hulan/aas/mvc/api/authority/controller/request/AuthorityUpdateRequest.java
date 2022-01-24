package kr.co.hulan.aas.mvc.api.authority.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AuthorityUpdateRequest", description="권한 수정 요청")
public class AuthorityUpdateRequest {
    @NotBlank(message = "권한 아이디는 필수입니다.")
    @ApiModelProperty(notes = "권한 아이디", required = true)
    private String authorityId;

    @NotBlank(message = "권한명은 필수입니다.")
    @ApiModelProperty(notes = "권한명", required = true)
    private String authorityName;

    @ApiModelProperty(notes = "설명")
    private String description;

    @ApiModelProperty(notes = "선택한 등급 목록")
    private List<Integer> selectedLevelList;

    @ApiModelProperty(notes = "권한 사용자 리스트")
    private List<String> authorityUserList;
}
