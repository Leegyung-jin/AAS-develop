package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import kr.co.hulan.aas.common.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosMemberUiComponentVo", description="메인 정보( UI 구성정보 )")
public class ImosMemberUiComponentVo {

  @ApiModelProperty(notes = "컴포넌트 배치 번호")
  private Long imucNo;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "사용자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "배포 페이지")
  private Integer deployPage;
  @ApiModelProperty(notes = "컴포넌트의 시작 x 좌표")
  private Integer posX;
  @ApiModelProperty(notes = "컴포넌트의 시작 y 좌표")
  private Integer posY;
  @ApiModelProperty(notes = "컴포넌트 아이디")
  private String hcmptId;
  @ApiModelProperty(notes = "컴포넌트명")
  private String hcmptName;
  @ApiModelProperty(notes = "컴포넌트 타입. 1: 메인 UI, 2: 컴포넌트 UI")
  private Integer uiType;
  @ApiModelProperty(notes = "컴포넌트 길이")
  private Integer width;
  @ApiModelProperty(notes = "컴포넌트 높이")
  private Integer height;
  @ApiModelProperty(notes = "사용자 지정 맟춤 데이터(String)")
  private String customData;

  @ApiModelProperty(notes = "사용자 지정 맟춤 데이터(json)")
  public Map<String,Object> getCustomDataJson(){
    if(StringUtils.isNotBlank(customData)){
      try{
        return JsonUtil.toStringMap(customData);
      }
      catch(Exception e){
      }
    }
    return null;
  }
}
