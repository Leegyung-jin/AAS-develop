package kr.co.hulan.aas.mvc.api.sensorMgr.controller.request;

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
@ApiModel(value="ExportSensorInfoDataRequest", description="센서 정보 데이터 Export 요청")
public class ExportSensorInfoDataRequest extends ConditionRequest {


    @ApiModelProperty(notes = "검색 조건명. wpName : 공사명(현장명), siCode : 안전센서번호, sdName : 구역, siPlace1 : 위치1, siPlace2 : 위치2")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "구역 아이디")
    private Integer sdIdx;

    @ApiModelProperty(notes = "유형")
    private String siType;

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
        if(StringUtils.isNotBlank(siType)){
            condition.put("siType", siType);
        }
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        if(sdIdx != null){
            condition.put("sdIdx", sdIdx);
        }

        return condition;
    }
}
