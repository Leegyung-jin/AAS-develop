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
@ApiModel(value = "AuthorityUserSearchListRequest", description = "관리자 페이지 권한 리스트(페이징) 요청")
public class AuthorityManagerListRequest extends DefaultPageRequest {
    @ApiModelProperty(notes = "검색 조건명. COMPLEX: 전체")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "사용자 아이디")
    private String mbId;

    @ApiModelProperty(notes = "등급 아이디")
    private Integer mbLevel;

    @Override
    public Map<String, Object> getConditionMap() {
        Map<String, Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        if( StringUtils.isNotBlank(mbId)){
            condition.put("mbId", mbId);
        }
        if(mbLevel != null){
            condition.put("mbLevel", mbLevel);
        }
        return condition;
    }
}
