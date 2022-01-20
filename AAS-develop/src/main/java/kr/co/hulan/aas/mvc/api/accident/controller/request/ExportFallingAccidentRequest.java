package kr.co.hulan.aas.mvc.api.accident.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ExportFallingAccidentRequest", description="낙하 이벤트 데이터 요청")
public class ExportFallingAccidentRequest extends ConditionRequest  {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX: 전체(근로자 아이디/이름, 현장명), mbId : 근로자 아이디, mbName : 근로자명, wpName: 현장명")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

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
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    if(startDate != null){
      condition.put("startDate", startDate);
    }
    if(endDate != null){
      condition.put("endDate", endDate);
    }
    return condition;
  }
}
