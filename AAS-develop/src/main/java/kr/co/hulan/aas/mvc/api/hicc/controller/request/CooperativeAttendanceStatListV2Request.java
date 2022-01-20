package kr.co.hulan.aas.mvc.api.hicc.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="CooperativeAttendanceStatListV2Request", description="협력사 출역 현황 리스트 V2 요청")
public class CooperativeAttendanceStatListV2Request extends DefaultPageRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, coopMbName: 협력사명 ")
  private String searchName = "coopMbName";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "검색 공종코드 리스트")
  private List<String> sectionCdList;

  @ApiModelProperty(notes = "검색 협력사 아이디 리스트")
  private List<String> coopMbIdList;

  @ApiModelProperty(notes = "정렬항목. 1: 협력사명, 2: 공종명. 3: 현장수, 4: 출력인원수, 5: 작업인원수.  디폴트는 3")
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
    if( sectionCdList != null && sectionCdList.size() != 0 ){
      condition.put("sectionCdList", sectionCdList);
    }
    if( coopMbIdList != null && coopMbIdList.size() != 0 ){
      condition.put("coopMbIdList", coopMbIdList);
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
