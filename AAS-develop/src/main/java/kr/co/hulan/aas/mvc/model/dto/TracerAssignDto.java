package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="TracerAssignDto", description="트랙커 할당 정보")
@AllArgsConstructor
@NoArgsConstructor
public class TracerAssignDto {

  @ApiModelProperty(notes = "트랙커 아이디")
  private String trackerId;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "트랙커 할당 로그 번호")
  private Long talNo;
}
