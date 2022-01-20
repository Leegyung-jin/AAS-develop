package kr.co.hulan.aas.mvc.api.member.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Data
public class ExportWorkerDataRequest extends ConditionRequest {


    @ApiModelProperty(notes = "검색 조건명. name:성명,  mbId:ID, memberShipNo:근로자번호, workPlace:가입업체, birthday:생년월일")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "검색 시작 등록일. ( UNIX_TIMESTAMP * 1000 )")
    private Date startDate;

    @ApiModelProperty(notes = "검색 종료 등록일. ( UNIX_TIMESTAMP * 1000 )")
    private Date endDate;


    @JsonIgnore
    @Override
    public Map<String,Object> getConditionMap(){
        Map<String,Object> condition = super.getConditionMap();
        condition.put("mbLevel", MemberLevel.WORKER.getCode());
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
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
