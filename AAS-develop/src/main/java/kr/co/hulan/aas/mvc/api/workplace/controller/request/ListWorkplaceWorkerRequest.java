package kr.co.hulan.aas.mvc.api.workplace.controller.request;

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
@ApiModel(value="ListWorkplaceWorkerRequest", description="근로자 현장 편입 리스트 요청")
public class ListWorkplaceWorkerRequest extends DefaultPageRequest {


    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(근로자명 + 공종 + 아이디), workerMbName : 근로자명, wpcWork : 공종, mbId : 근로자 아이디 ( 휴대폰 번호 )")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

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
        if(StringUtils.isNotBlank(coopMbId)){
            condition.put("coopMbId", coopMbId);
        }
        return condition;
    }

}
