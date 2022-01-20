package kr.co.hulan.aas.mvc.api.worksection.controller.request;

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
@ApiModel(value="ExportWorkSectionDataRequest", description="공정 데이터 Export 요청")
public class ExportWorkSectionDataRequest extends ConditionRequest  {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX: 전체(공정코드, 공정명), sectionCd: 공정코드, sectionName: 공정명")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "상위공정코드. TOPLEVEL : 최상위 공정(공정A) 조회, 그외는 전달된 공정코드가 상위공정코드인 공정코드 조회 ")
    private String parentSectionCd;

    @JsonIgnore
    @Override
    public Map<String,Object> getConditionMap(){
        Map<String,Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        if(StringUtils.isNotBlank(parentSectionCd)){
            condition.put("parentSectionCd", parentSectionCd);
        }
        return condition;
    }
}
