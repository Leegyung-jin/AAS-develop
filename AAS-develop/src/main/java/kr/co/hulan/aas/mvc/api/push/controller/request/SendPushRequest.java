package kr.co.hulan.aas.mvc.api.push.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SendPushRequest", description="Push 전송 요청")
public class SendPushRequest extends ConditionRequest {

  @NotEmpty
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @NotNull
  @Size(min=1)
  @ApiModelProperty(notes = "대상 근로자")
  private List<String> mbIdList;

  @NotEmpty
  @ApiModelProperty(notes = "제목", required = true)
  private String puSubject;

  @NotEmpty
  @ApiModelProperty(notes = "내용", required = true)
  private String puContent;


  @ApiModelProperty(notes = "긴급 알림 여부.  1 : Yes")
  private Integer puChk;


  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    if(mbIdList != null){
      condition.put("mbIdList", mbIdList);
    }
    return condition;
  }

}
