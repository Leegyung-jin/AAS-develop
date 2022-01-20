package kr.co.hulan.aas.mvc.api.safetySituation.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Data
@ApiModel(value="ListWorkerSafetySituationDetailRequest", description="근로자 안전 현황 상세 세부 기록 요청")
public class ListWorkerSafetySituationDetailRequest extends ConditionRequest {

    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotEmpty
    @ApiModelProperty(notes = "근로자 아이디", required = true)
    private String mbId;

    @NotNull
    @ApiModelProperty(notes = "기준일. yyyy-MM-dd", required = true)
    private Date slDatetime;

    @JsonIgnore
    @Override
    public Map<String,Object> getConditionMap(){
        Map<String,Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        if(StringUtils.isNotBlank(mbId)){
            condition.put("mbId", mbId);
        }
        if(slDatetime != null){
            condition.put("sltDatetime", DateUtils.truncate(slDatetime, Calendar.DATE));
            condition.put("startDate", DateUtils.truncate(slDatetime, Calendar.DATE));
            condition.put("endDate", DateUtils.addMilliseconds(DateUtils.truncate(DateUtils.addDays(slDatetime, 1), Calendar.DATE), -1)) ;
        }
        return condition;
    }
}
