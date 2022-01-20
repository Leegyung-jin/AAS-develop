package kr.co.hulan.aas.mvc.api.member.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.Condition;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Data
public class ListConstructionCompanyRequest extends DefaultPageRequest {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(건설사명 + 사업자등록번호), ccName:건설사명,  ccNum:사업자등록번호")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;


    @JsonIgnore
    @Override
    public Map<String,Object> getConditionMap(){
        Map<String,Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        return condition;
    }
}
