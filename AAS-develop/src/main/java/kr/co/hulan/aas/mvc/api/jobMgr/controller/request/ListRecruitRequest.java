package kr.co.hulan.aas.mvc.api.jobMgr.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListRecruitRequest", description="구인 리스트 요청")
public class ListRecruitRequest extends DefaultPageRequest {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(공사명 + 협력사명 + 공고명), wpName : 공사명(현장명), coopMbName : 협력사명, rcTitle : 공고명 ")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "공종A")
    private String workSectionA;

    @ApiModelProperty(notes = "공종B")
    private String workSectionB;

    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;

    @JsonIgnore
    @Override
    public Map<String,Object> getConditionMap(){
        Map<String,Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        if(StringUtils.isNotBlank(ccId)){
            condition.put("ccId", ccId);
        }
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        if(StringUtils.isNotBlank(workSectionA)){
            condition.put("workSectionA", workSectionA);
        }
        if(StringUtils.isNotBlank(workSectionB)){
            condition.put("workSectionB", workSectionB);
        }
        if(StringUtils.isNotBlank(coopMbId)){
            condition.put("coopMbId", coopMbId);
        }

        return condition;
    }
}
