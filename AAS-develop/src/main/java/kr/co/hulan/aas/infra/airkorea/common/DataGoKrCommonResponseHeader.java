package kr.co.hulan.aas.infra.airkorea.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="DataGoKrCommonResponseHeader", description="공공 API 응답 Header")
public class DataGoKrCommonResponseHeader {
  @ApiModelProperty(notes = "결과코드")
  private String resultCode;

  @ApiModelProperty(notes = "결과메세지")
  private String resultMsg;


}
