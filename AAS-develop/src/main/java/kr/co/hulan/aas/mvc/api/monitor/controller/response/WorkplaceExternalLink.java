package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@ApiModel(value="WorkplaceExternalLink", description="현장 외부 링크 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceExternalLink {

  @ApiModelProperty(notes = "외부 링크 넘버")
  private Integer linkNo;
  @ApiModelProperty(notes = "외부 링크명")
  private String linkName;
  @ApiModelProperty(notes = "외부 링크 URL")
  private String linkUrl;
  @ApiModelProperty(notes = "외부 링크 상태. 1: ON")
  private Integer linkStatus;

}
