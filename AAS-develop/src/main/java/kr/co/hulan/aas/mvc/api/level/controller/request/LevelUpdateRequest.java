package kr.co.hulan.aas.mvc.api.level.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="LevelUpdateRequest", description="계정 등급 수정 요청")
public class LevelUpdateRequest {

    @NotNull(message = "등급 코드는 필수입니다.")
    @ApiModelProperty(notes = "등급 코드", required = true)
    private Integer mbLevel;

    @NotBlank(message = "등급명은 필수입니다.")
    @ApiModelProperty(notes = "등급명", required = true)
    private String mbLevelName;

    @ApiModelProperty(notes = "설명")
    private String description;

    @ApiModelProperty(notes = "선택한 권한 목록")
    private List<String> selectedAuthorityList;

}
