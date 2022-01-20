package kr.co.hulan.aas.mvc.api.authority.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AuthorityResponse", description="권한을 등록한 사용자 리스트 응답")
public class AuthorityResponse {
    @ApiModelProperty(notes = "전체 수")
    private Integer totalCount;
    @ApiModelProperty(notes = "사용자 리스트")
    private List<AuthorityDto> list;
}
