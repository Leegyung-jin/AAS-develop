package kr.co.hulan.aas.mvc.api.hicc.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="CooperativeAttendanceStatExportV2Request", description="협력사 출역 현황 데이터 Export V2 요청")
public class CooperativeAttendanceStatExportV2Request extends ConditionRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, coopMbName: 협력사명 ")
  private String searchName = "coopMbName";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "검색 공종코드 리스트")
  private List<String> sectionCdList;

  @ApiModelProperty(notes = "검색 협력사 아이디 리스트")
  private List<String> coopMbIdList;

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
    condition = AuthenticationHelper.addAdditionalConditionByLevel(condition);
    return condition;
  }
}
