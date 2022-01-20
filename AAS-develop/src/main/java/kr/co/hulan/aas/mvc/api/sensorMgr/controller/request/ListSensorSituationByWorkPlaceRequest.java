package kr.co.hulan.aas.mvc.api.sensorMgr.controller.request;

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
@ApiModel(value="ListSensorSituationByWorkPlaceRequest", description="현장별 센서 현황 리스트 요청")
public class ListSensorSituationByWorkPlaceRequest extends DefaultPageRequest {


    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(공사명), wpName : 공사명(현장명)")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    /*
    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "구역 아이디")
    private Integer sdIdx;

     */

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
        /*
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        if(sdIdx != null){
            condition.put("sdIdx", sdIdx);
        }

         */

        return condition;
    }
}
