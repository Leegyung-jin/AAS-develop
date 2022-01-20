package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="MemberLevelDto", description="멤버 레벨(등급) 정보")
@AllArgsConstructor
@NoArgsConstructor
public class MemberLevelDto {

  @ApiModelProperty(notes = "계정 레벨(등급)")
  private Integer mbLevel;

  @ApiModelProperty(notes = "계정 레벨(등급)명")
  private String mbLevelName;

  @ApiModelProperty(notes = "설명")
  private String description;


}
