package kr.co.hulan.aas.mvc.api.orderoffice.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OfficeWorkplaceGroupPagingListRequest", description="발주사 현장그룹 페이징 리스트 요청")
public class OfficeWorkplaceGroupPagingListRequest extends DefaultPageRequest  {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, officeName : 발주사명, officeGrpName : 발주사 현장그룹명")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "발주사 관리번호")
  private Long officeNo;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    if( officeNo != null ){
      condition.put("officeNo", officeNo);
    }
    return condition;
  }
}
