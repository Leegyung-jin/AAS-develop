package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosWorkplaceMainRequest", description="현장 메인 정보 요청")
public class ImosWorkplaceMainRequest {

  //@Min(value = 1, message = "페이지는 2개만 허용됩니다.") @Max(value = 2, message = "페이지는 2개만 허용됩니다.")
  @ApiModelProperty(notes = "UI Component 페이지. 없으면 전달안함")
  private Integer deployPage;

  @ApiModelProperty(notes = "현장 정보 필요 유무")
  private boolean workplace;
  @ApiModelProperty(notes = "날씨 정보 필요 유무")
  private boolean weather;

  @ApiModelProperty(notes = "최근 공지 필요 유무")
  private boolean notice;
  @ApiModelProperty(notes = "미세먼지 수치 필요 유무")
  private boolean dust;
}
