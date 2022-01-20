package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="FallingAccidentDto", description="낙하 이벤트 정보")
@AllArgsConstructor
@NoArgsConstructor
public class FallingAccidentDto {

  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;

  @ApiModelProperty(notes = "근로자명")
  private String mbName;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "이벤트발생 시간")
  private Date measureTime;

  @ApiModelProperty(notes = "팝업 발생 여부")
  private Integer dashboardPopup;

  @ApiModelProperty(notes = "센서 아이디")
  private Integer siIdx;

  @ApiModelProperty(notes = "유형")
  private String siType;

  @ApiModelProperty(notes = "위치1")
  private String siPlace1;

  @ApiModelProperty(notes = "위치2")
  private String siPlace2;

  @ApiModelProperty(notes = "구역명")
  private String sdName;
}
