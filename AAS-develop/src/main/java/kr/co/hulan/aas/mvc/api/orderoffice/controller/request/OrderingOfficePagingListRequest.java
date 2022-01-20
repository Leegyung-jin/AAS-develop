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
@ApiModel(value="OrderingOfficePagingListRequest", description="발주사 페이징 리스트 요청")
public class OrderingOfficePagingListRequest extends DefaultPageRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, officeName : 발주사명, telephone : 전화번호")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    return condition;
  }
}
