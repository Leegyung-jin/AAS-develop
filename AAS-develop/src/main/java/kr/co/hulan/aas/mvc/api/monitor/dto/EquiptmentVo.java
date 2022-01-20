package kr.co.hulan.aas.mvc.api.monitor.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EquiptmentVo", description="장비 정보")
public class EquiptmentVo {

  @ApiModelProperty(notes = "장비 코드")
  private Integer equipmentType;

  @ApiModelProperty(notes = "장비명")
  private String equipmentName;

  @ApiModelProperty(notes = "장비 번호 (차량번호)")
  private String equipmentNo;

  @ApiModelProperty(notes = "디바이스 식별자")
  private String deviceId;


}
