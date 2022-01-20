package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceDeviceStateVo", description="장비/영상 현황")
public class HiccWorkplaceDeviceStateVo {

  @ApiModelProperty(notes = "장비 구분", hidden = true)
  private Integer deviceType;

  @ApiModelProperty(notes = "장비 구분명", hidden = true)
  private String deviceTypeName;

  @ApiModelProperty(notes = "장비수")
  private Integer deviceCount;

  @ApiModelProperty(notes = "장비 구분명")
  public String getDeviceTypeName(){
    if(StringUtils.isNotBlank(deviceTypeName)){
      return deviceTypeName;
    }
    DeviceType device = DeviceType.get(deviceType);
    return device != null ? device.getName() : "";
  }
}
