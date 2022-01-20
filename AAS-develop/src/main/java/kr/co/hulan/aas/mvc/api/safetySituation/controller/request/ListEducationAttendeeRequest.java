package kr.co.hulan.aas.mvc.api.safetySituation.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListEducationAttendeeRequest", description="안전조회 참석/미참석자 검색 요청")
public class ListEducationAttendeeRequest extends ConditionRequest {

    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotEmpty
    @ApiModelProperty(notes = "협력사 아이디", required = true)
    private String coopMbId;

    @ApiModelProperty(notes = "참석 여부. '' : 전체 , '0' : 미참석,  '1' : 참석   ")
    private String attendStatus;

    @NotNull(message = "검색 기준일이 있어야 합니다.")
    @ApiModelProperty(notes = "검색 기준일. ( UNIX_TIMESTAMP * 1000 )", required = true)
    private Date targetDate;

    @JsonIgnore
    @Override
    public Map<String,Object> getConditionMap(){
        Map<String,Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        if(StringUtils.isNotBlank(coopMbId)){
            condition.put("coopMbId", coopMbId);
        }
        if(StringUtils.isNotBlank(attendStatus)){
            condition.put("attendStatus", attendStatus);
        }
        if(targetDate != null){
            condition.put("targetDate", DateUtils.truncate(targetDate, Calendar.DATE));
            condition.put("startDate", DateUtils.truncate(targetDate, Calendar.DATE));
            condition.put("endDate", DateUtils.addMilliseconds(DateUtils.truncate(DateUtils.addDays(targetDate, 1), Calendar.DATE), -1)) ;
        }
        return condition;
    }
}
