package kr.co.hulan.aas.mvc.api.member.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ConCompanyManagerExportRequest", description="건설사 관리자 데이터 Export 요청")
public class ConCompanyManagerExportRequest extends ConditionRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, ccName : 건설사명, mbName:성명,  mbId:회원아이디, telephone: 전화번호 ")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;

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
    if(startDate != null){
      condition.put("startDate", DateUtils.truncate(startDate, Calendar.DATE));
    }
    if(endDate != null){
      condition.put("endDate", DateUtils.addMilliseconds(DateUtils.truncate(DateUtils.addDays(endDate, 1), Calendar.DATE), -1));
    }
    return condition;
  }
}
