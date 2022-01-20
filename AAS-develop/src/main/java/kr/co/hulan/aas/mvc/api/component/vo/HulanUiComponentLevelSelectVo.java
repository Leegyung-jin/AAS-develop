package kr.co.hulan.aas.mvc.api.component.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HulanUiComponentLevelSelectVo", description="UI 컴보넌트 지원 계정 레벨(등급) 선택 정보")
public class HulanUiComponentLevelSelectVo extends HulanUiComponentLevelVo {

  @ApiModelProperty(notes = "지원여부. 0: 미지원, 1: 지원")
  private Integer selected;

}
