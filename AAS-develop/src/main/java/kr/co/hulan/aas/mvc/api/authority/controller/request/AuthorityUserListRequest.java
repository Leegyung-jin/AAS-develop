package kr.co.hulan.aas.mvc.api.authority.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AuthorityUserSearchListRequest", description = "권한 등록 가능한 사용자 리스트(페이징) 요청")
public class AuthorityUserListRequest extends DefaultPageRequest {
    @ApiModelProperty(notes = "검색 조건명. COMPLEX: 전체, authorityId: 권한아이디, authorityName: 권한명")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "등급 아이디")
    private Integer mbLevel;

    @Override
    public Map<String, Object> getConditionMap() {
        Map<String, Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        if(mbLevel != null){
            condition.put("mbLevel", mbLevel);
        }
        return condition;
    }
}
