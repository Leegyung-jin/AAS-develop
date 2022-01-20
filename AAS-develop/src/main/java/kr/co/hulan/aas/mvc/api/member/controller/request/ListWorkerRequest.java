package kr.co.hulan.aas.mvc.api.member.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.model.req.Condition;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ListWorkerRequest extends DefaultPageRequest {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(성명 + ID + 근로자번호 + 가입업체 + 생년월일), name:성명,  mbId:ID, memberShipNo:근로자번호, workPlace:가입업체, birthday:생년월일")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "검색 시작 등록일. ( UNIX_TIMESTAMP * 1000 )")
    private Date startDate;

    @ApiModelProperty(notes = "검색 종료 등록일. ( UNIX_TIMESTAMP * 1000 )")
    private Date endDate;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "제외 현장 아이디")
    private String excludeWpId;

    @JsonIgnore
    @Override
    public List<Condition> getConditionList(){
        List<Condition> condition = super.getConditionList();
        condition.add(new Condition(Condition.TYPE.EQUALS, "mbLevel", MemberLevel.WORKER.getCode()));
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.add(new Condition(Condition.TYPE.LIKE, searchName, "%"+searchValue+"%"));
        }

        if(startDate != null){
            condition.add(new Condition(Condition.TYPE.GREATER_OR_EQUALS, "registDate", DateUtils.truncate(startDate, Calendar.DATE)) );
        }
        if(endDate != null){
            condition.add(new Condition(Condition.TYPE.LESS_OR_EQUALS, "registDate", DateUtils.addMilliseconds(DateUtils.truncate(DateUtils.addDays(endDate, 1), Calendar.DATE), -1)) );
        }
        return condition;
    }

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
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        if(StringUtils.isNotBlank(excludeWpId)){
            condition.put("excludeWpId", excludeWpId);
        }
        return condition;
    }
}
