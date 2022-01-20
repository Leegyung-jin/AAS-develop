package kr.co.hulan.aas.mvc.api.level.controller.request;

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
@ApiModel(value = "LevelExportRequest", description = "등급 정보 Export 요청")
public class LevelExportRequest extends ConditionRequest {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, mbLevel : 계정 레벨(등급), mbLevelName: : 계정 레벨(등급)명")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건 값")
    private String searchValue;

    @JsonIgnore
    @Override
    public Map<String, Object> getConditionMap(){
        Map<String, Object> condition = super.getConditionMap();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        return condition;
    }

}
