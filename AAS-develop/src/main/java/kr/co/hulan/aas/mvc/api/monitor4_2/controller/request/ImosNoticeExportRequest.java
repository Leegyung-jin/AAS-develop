package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
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
@ApiModel(value="ImosNoticeExportRequest", description="IMOS 현장 공지사항 리스트(Export) 요청")
public class ImosNoticeExportRequest extends ConditionRequest  {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체")
  private String searchName = "COMPLEX";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

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
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    condition = AuthenticationHelper.addAdditionalConditionByLevel(condition);
    return condition;
  }
}
