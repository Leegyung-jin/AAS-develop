package kr.co.hulan.aas.mvc.api.jobMgr.controller.request;

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
@ApiModel(value="ExportRecruitApplyDataRequest", description="구직 데이터 Export 요청")
public class ExportRecruitApplyDataRequest extends ConditionRequest {

    @ApiModelProperty(notes = "검색 조건명. wpName : 공사명(현장명), coopMbName : 협력사명, rcTitle : 공고명, mbId : 연락처 ")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "구인 아이디")
    private Integer rcIdx;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "구직 상태. 0 : 대기, 1 : 심사중, 2 : 현장편입 ")
    private String raStatus;

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
        if( rcIdx != null ){
            condition.put("rcIdx", rcIdx);
        }
        if(StringUtils.isNotBlank(raStatus)){
            condition.put("raStatus", raStatus);
        }
        if(StringUtils.isNotBlank(coopMbId)){
            condition.put("coopMbId", coopMbId);
        }
        return condition;
    }

}
