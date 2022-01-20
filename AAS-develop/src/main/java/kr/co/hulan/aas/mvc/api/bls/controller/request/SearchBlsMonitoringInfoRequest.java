package kr.co.hulan.aas.mvc.api.bls.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SearchBlsMonitoringInfoRequest", description="BLS monitoring 정보 요청")
public class SearchBlsMonitoringInfoRequest {

  @NotEmpty
  @ApiModelProperty(notes = "현장 아이디.", required = true)
  private String wpId;

  @ApiModelProperty(notes = "빌딩 넘버(SEQ).")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩 층. 0 이면 전체 층을 의미")
  private Integer floor;

}
