package kr.co.hulan.aas.mvc.api.hicc.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccImosNoticeExportRequest", description="[통합관제] 현장 공지사항 데이터 Export 요청")
public class HiccImosNoticeExportRequest extends ConditionRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체")
  private String searchName = "COMPLEX";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "검색 대상 현장 아이디 리스트. 없으면 전체현장")
  private List<String> wpIdList;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  private Date endDate;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    if( startDate != null ){
      condition.put("startDate", startDate);
    }
    if( endDate != null ){
      condition.put("endDate", endDate);
    }
    if( wpIdList != null && wpIdList.size() > 0 ){
      condition.put("wpIdList", wpIdList);
    }
    condition = AuthenticationHelper.addAdditionalConditionByLevel(condition);
    return condition;
  }
}
