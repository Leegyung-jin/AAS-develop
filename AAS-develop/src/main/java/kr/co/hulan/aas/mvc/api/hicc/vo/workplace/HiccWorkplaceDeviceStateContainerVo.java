package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceDeviceStateContainerVo", description="장비/영상 솔루션 현황")
public class HiccWorkplaceDeviceStateContainerVo {

  private List<HiccWorkplaceDeviceStateVo> deviceList;

  private List<HiccWorkplaceDeviceStateVo> imageDeviceList;

}
