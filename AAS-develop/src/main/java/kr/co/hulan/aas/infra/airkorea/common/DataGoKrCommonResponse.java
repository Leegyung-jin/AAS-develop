package kr.co.hulan.aas.infra.airkorea.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="DataGoKrCommonResponse", description="공공 API 응답")
public class DataGoKrCommonResponse<T> {
  @ApiModelProperty(notes = "응답 전문")
  private DataGoKrCommonResponseHeader header;

  @ApiModelProperty(notes = "응답 전문")
  private T body;
}
