package kr.co.hulan.aas.mvc.api.msg.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListAdminMsgInfoRequest", description="현장 관리자 알림 메시지 리스트 요청")
public class ListAdminMsgInfoRequest extends DefaultPageRequest  {


  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, subject: 제목, msg : 알림내용")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "알림 유형. 100: 위험지역 접근 알림, 120: 앱 기능 해제 알림. 없으면 전체")
  private Integer msgType;

  @ApiModelProperty(notes = "검색 시작일. ( UNIX_TIMESTAMP * 1000 )")
  private Date startDate;

  @ApiModelProperty(notes = "검색 종료일. ( UNIX_TIMESTAMP * 1000 )")
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
    if(msgType != null){
      condition.put("msgType", msgType);
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
