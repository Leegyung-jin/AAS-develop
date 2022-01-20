package kr.co.hulan.aas.mvc.api.monitor4_1.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
@ApiModel(value="MonitorMainRequest", description="현장 메인 정보")
public class MonitorMainRequest {

  @ApiModelProperty(notes = "현장 정보 필요 유무")
  private boolean workplace;
  @ApiModelProperty(notes = "UI Component 구성정보 필요 유무")
  private boolean uiComponent;
  @ApiModelProperty(notes = "날씨 정보 필요 유무")
  private boolean weather;
  @ApiModelProperty(notes = "최근 공지 필요 유무")
  private boolean notice;
  @ApiModelProperty(notes = "미세먼지 수치 필요 유무")
  private boolean dust;

}
