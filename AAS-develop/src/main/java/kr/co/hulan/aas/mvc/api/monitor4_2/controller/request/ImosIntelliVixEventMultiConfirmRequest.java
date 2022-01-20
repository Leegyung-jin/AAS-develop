package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@ApiModel(value="ImosIntelliVixEventMultiConfirmRequest", description="지능형 CCTV 이벤트 일괄 조치 확인 요청")
public class ImosIntelliVixEventMultiConfirmRequest {


  @NotNull(message = "이벤트 번호는 필수입니다.")
  @Size(min = 1, message = "이벤트 번호를 최소 한개는 선택하셔야 합니다.")
  @ApiModelProperty(notes = "조치 대상 이벤트 번호 리스트")
  private List<Long> eventNoList;

  @NotNull(message="조치방법은 필수입니다")
  @EnumCodeValid(target = NvrEventActionMethod.class, message = "조치방법이 올바르지 않습니다.")
  @ApiModelProperty(notes = "조치방법. 1: 확인 완료, 2: 감지오류")
  private Integer actionMethod;

}
