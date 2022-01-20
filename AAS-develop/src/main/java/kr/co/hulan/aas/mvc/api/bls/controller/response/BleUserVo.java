package kr.co.hulan.aas.mvc.api.bls.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="BleUserVo", description="BLE 유저 정보")
@AllArgsConstructor
@NoArgsConstructor
public class BleUserVo {

  @ApiModelProperty(notes = "현장아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;
  @ApiModelProperty(notes = "근로자 아이디(전화번호)")
  private String mbId;
  @ApiModelProperty(notes = "성명")
  private String mbName;

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;
  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;
  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;
  @ApiModelProperty(notes = "빌딩 층명")
  private String floorName;

  @ApiModelProperty(notes = "센서 아이디")
  private Integer siIdx;
  @ApiModelProperty(notes = "구역명")
  private String sdName;
  @ApiModelProperty(notes = "위치1")
  private String siPlace1;
  @ApiModelProperty(notes = "위치2")
  private String siPlace2;

  @ApiModelProperty(notes = "접근 시간")
  private Date slrDatetime;
}
