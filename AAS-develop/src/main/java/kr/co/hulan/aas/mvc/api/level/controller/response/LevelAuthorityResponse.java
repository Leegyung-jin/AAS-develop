package kr.co.hulan.aas.mvc.api.level.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LevelAuthorityResponse", description = "등급에 포함된 권한 목록")
public class LevelAuthorityResponse {

    @ApiModelProperty(notes = "등급에 포함된 권한 목록")
    private AuthorityDto selectedAuthorityDto;
}
