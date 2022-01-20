package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.code.MacAddressType;
import kr.co.hulan.aas.common.code.NvrEventActionMethod;
import kr.co.hulan.aas.common.validator.EnumCodeValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosIntelliVixEventConfirmRequest", description="지능형 CCTV 이벤트 조치 확인 요청")
public class ImosIntelliVixEventConfirmRequest {

  @NotNull(message="조치방법은 필수입니다")
  @EnumCodeValid(target = NvrEventActionMethod.class, message = "조치방법이 올바르지 않습니다.")
  @ApiModelProperty(notes = "조치방법. 1: 확인 완료, 2: 감지오류")
  private Integer actionMethod;

  @ApiModelProperty(notes = "메모")
  private String memo;
}
