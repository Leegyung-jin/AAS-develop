package kr.co.hulan.aas.mvc.api.safetySituation.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.req.Condition;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ListWorkplaceSafetySituationRequest extends DefaultPageRequest {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(공사명), wpName:공사명")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;

    @ApiModelProperty(notes = "검색 시작 출근일. ( UNIX_TIMESTAMP * 1000 )")
    private Date startDate;

    @ApiModelProperty(notes = "검색 종료 출근일. ( UNIX_TIMESTAMP * 1000 )")
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
        if(startDate != null){
            condition.put("startDate", DateUtils.truncate(startDate, Calendar.DATE));
        }
        if(endDate != null){
            condition.put("endDate", DateUtils.addMilliseconds(DateUtils.truncate(DateUtils.addDays(endDate, 1), Calendar.DATE), -1)) ;
        }
        return condition;
    }

}
