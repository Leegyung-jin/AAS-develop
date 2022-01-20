package kr.co.hulan.aas.mvc.api.monitor4_1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EquipmentSituationVo", description="장비 현황 정보")
public class EquipmentSituationVo {

  @ApiModelProperty(notes = "장비 타입 코드")
  private Integer equipmentType;
  @ApiModelProperty(notes = "장비명")
  private String equipmentName;
  @ApiModelProperty(notes = "장비 아이콘 URL")
  private String iconUrl;
  @ApiModelProperty(notes = "총 등록수")
  private Long totalCount;
  @ApiModelProperty(notes = "현재 운영수")
  private Long opeCount;
  @ApiModelProperty(notes = "금일 운영 이력이 있는 장비수")
  private Long todayCount;
}
