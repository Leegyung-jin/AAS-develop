package kr.co.hulan.aas.mvc.api.authority.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AuthorityExportRequest", description = "권한 정보 Export 요청")
public class AuthorityExportRequest extends ConditionRequest {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, authorityId : 권한ID, authorityName : 권한명")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @JsonIgnore
    @Override
    public Map<String, Object> getConditionMap(){
        Map<String, Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        return condition;
    }
}
