package kr.co.hulan.aas.mvc.api.monitor4_1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="DetectedSensorWorkerVo", description="Sensor 접근자 정보")
public class DetectedSensorWorkerVo {

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;

  @ApiModelProperty(notes = "회원 아이디")
  private String mbId;

  @ApiModelProperty(notes = "회원 성명")
  private String mbName;

  @ApiModelProperty(notes = "공종A명 ( 협력사 공종 )")
  private String workSectionNameA;

  @ApiModelProperty(notes = "공종B명 ( 근로자 공종 )")
  private String workSectionNameB;

  @ApiModelProperty(notes = "접근 시간")
  private Date slrDatetime;

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

  @ApiModelProperty(notes = "메모")
  private String memo;

  @ApiModelProperty(notes = "빌딩 넘버")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;



}
