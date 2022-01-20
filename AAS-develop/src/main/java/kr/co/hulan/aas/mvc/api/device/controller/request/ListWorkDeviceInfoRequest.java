package kr.co.hulan.aas.mvc.api.device.controller.request;


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
@ApiModel(value="ListWorkDeviceInfoRequest", description="현장 디바이스 정보 검색 요청")
public class ListWorkDeviceInfoRequest extends DefaultPageRequest  {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(공사명, 디바이스 식별자, macAddress), wpName : 공사명(현장명), deviceId : 디바이스 식별자, macAddress: mac address")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "디바이스 타입")
    private Integer deviceType;

    @JsonIgnore
    @Override
    public Map<String,Object> getConditionMap(){
        Map<String,Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        if( deviceType != null ){
            condition.put("deviceType", deviceType);
        }
        return condition;
    }
}
