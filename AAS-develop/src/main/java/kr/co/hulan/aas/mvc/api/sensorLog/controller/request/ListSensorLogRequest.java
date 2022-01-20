package kr.co.hulan.aas.mvc.api.sensorLog.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorLogRequest", description="안전세부현황 리스트 요청")
public class ListSensorLogRequest extends DefaultPageRequest {


    @ApiModelProperty(notes = "검색 조건명. ccName : 건설사명, wpName : 공사명(현장명), coopMbName : 현장명, mbId : 근로자 아이디, mbName : 근로자명")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;

    @ApiModelProperty(notes = "구역 아이디")
    private Integer sdIdx;

    @ApiModelProperty(notes = "센서 타입")
    private String siType;

    @ApiModelProperty(notes = "위치1")
    private String siPlace1;

    @ApiModelProperty(notes = "위치2")
    private String siPlace2;

    @ApiModelProperty(notes = "검색 시작 등록일. ( UNIX_TIMESTAMP * 1000 )")
    private Date startDate;

    @ApiModelProperty(notes = "검색 종료 등록일. ( UNIX_TIMESTAMP * 1000 )")
    private Date endDate;


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
        if(sdIdx != null){
            condition.put("sdIdx", sdIdx);
        }
        if(StringUtils.isNotBlank(siType)){
            condition.put("siType", siType);
        }
        if(StringUtils.isNotBlank(siPlace1)){
            condition.put("siPlace1", siPlace1);
        }
        if(StringUtils.isNotBlank(siPlace2)){
            condition.put("siPlace2", siPlace2);
        }
        if(startDate != null){
            condition.put("startDate", DateUtils.truncate(startDate, Calendar.DATE));
        }
        if(endDate != null){
            condition.put("endDate", DateUtils.addMilliseconds(DateUtils.truncate(DateUtils.addDays(endDate, 1), Calendar.DATE), -1));
        }
        return condition;
    }
}
