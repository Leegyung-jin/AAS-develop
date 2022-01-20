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
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccImosNoticePagingRequest", description="[통합관제] 현장 공지사항 리스트(페이징) 요청")
public class HiccImosNoticePagingRequest extends DefaultPageRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체")
  private String searchName = "COMPLEX";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  @ApiModelProperty(notes = "검색 시작일. ms 의 long 값 내지는 yyyyMMdd 포맷의 문자")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  @ApiModelProperty(notes = "검색 종료일. ms 의 long 값 내지는 yyyyMMdd 포맷의 문자")
  private Date endDate;

  @ApiModelProperty(notes = "검색 대상 현장 아이디 리스트. 없으면 전체현장")
  private List<String> wpIdList;

  @ApiModelProperty(notes = "정렬항목. 1: 전송일(생성일), 2: 현장요약명. 디폴트는 1")
  private Integer ordBy;

  @ApiModelProperty(notes = "정렬순서. 0: 내림차순(DESC), 1: 오름차순(ASC). 디폴트는 0")
  private Integer ordAsc;

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
    if( ordBy != null ){
      condition.put("ordBy", ordBy);
    }
    if( ordAsc != null ){
      condition.put("ordAsc", ordAsc);
    }
    condition = AuthenticationHelper.addAdditionalConditionByLevel(condition);
    return condition;
  }
}
