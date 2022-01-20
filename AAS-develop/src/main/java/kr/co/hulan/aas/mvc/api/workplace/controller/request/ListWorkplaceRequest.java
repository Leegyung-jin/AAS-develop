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
@ApiModel(value="ListWorkplaceRequest", description="현장 리스트 요청")
public class ListWorkplaceRequest extends DefaultPageRequest  {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX: 전체(공사명), wpName : 공사명")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    @ApiModelProperty(notes = "GPS 제공 여부. '':전체, '0': 제공안함, '1': 제공")
    private String gps;

    @ApiModelProperty(notes = "BLS 제공 여부. '':전체, '0': 제공안함, '1': 제공")
    private String bls;

    @ApiModelProperty(notes = "출입게이트 할당 여부. 0: 없음, 1: 할당")
    private Integer entergate;

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
        if(StringUtils.isNotBlank(gps)){
            condition.put("gps", gps);
        }
        if(StringUtils.isNotBlank(bls)){
            condition.put("bls", bls);
        }
        if(entergate != null){
            condition.put("entergate", entergate);
        }
        return condition;
    }


}

