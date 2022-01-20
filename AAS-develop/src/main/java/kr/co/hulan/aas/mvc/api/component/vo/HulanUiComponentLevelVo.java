package kr.co.hulan.aas.mvc.api.component.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HulanUiComponentLevelVo", description="UI 컴보넌트 지원 계정 레벨(등급) 정보")
public class HulanUiComponentLevelVo {

  @NotNull(message = "계정 레벨(등급)은 필수입니다")
  @ApiModelProperty(notes = "계정 레벨(등급)")
  private Integer mbLevel;

  @ApiModelProperty(notes = "컴포넌트 아이디")
  private String hcmptId;

  @ApiModelProperty(notes = "계정 레벨(등급)명")
  private String mbLevelName;
}
