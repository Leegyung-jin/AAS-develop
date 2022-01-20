package kr.co.hulan.aas.mvc.api.safetySituation.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Data
@ApiModel(value="ListWorkerFatiqueRequest", description="근로자 피로도 현황 검색 요청")
public class ListWorkerFatiqueRequest extends DefaultPageRequest  {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(협력사명 + 근로자 아이디 + 근로자명), coopMbName : 협럭사명, workerMbId:근로자 아이디, workerMbName: 근로자명")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "협력사")
    private String coopMbId;

    @NotNull(message = "검색 기준일이 있어야 합니다.")
    @ApiModelProperty(notes = "검색 기준일. ( UNIX_TIMESTAMP * 1000 )", required = true)
    private Date targetDate;

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
        if(targetDate != null){
            condition.put("targetDate", DateUtils.truncate(targetDate, Calendar.DATE));
            condition.put("startDate", DateUtils.truncate(targetDate, Calendar.DATE));
            condition.put("endDate", DateUtils.addMilliseconds(DateUtils.truncate(DateUtils.addDays(targetDate, 1), Calendar.DATE), -1)) ;
        }
        return condition;
    }
}
