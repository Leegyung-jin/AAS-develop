package kr.co.hulan.aas.mvc.api.bls.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@ApiModel(value="WorkerBySensor", description="센서 접근 근로자 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkerBySensor {

  @ApiModelProperty(notes = "센서 아이디")
  private Integer siIdx;

  @ApiModelProperty(notes = "안전센서번호")
  private String siCode;

  @ApiModelProperty(notes = "유형")
  private String siType;

  /*
  @ApiModelProperty(notes = "위치1")
  private String siPlace1;

  @ApiModelProperty(notes = "위치2")
  private String siPlace2;

  @ApiModelProperty(notes = "현장관리자 PUSH알림. 0 : 수신안함, 1 :수신 ")
  private Integer siPush;

  @ApiModelProperty(notes = "등록일")
  private java.util.Date siDatetime;

  @ApiModelProperty(notes = "센서 uuid")
  private String uuid;
   */

  @ApiModelProperty(notes = "센서 major identifier")
  private Integer major;

  @ApiModelProperty(notes = "센서 minor identifier")
  private Integer minor;

  @ApiModelProperty(notes = "건설사명")
  private String ccName;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;

  @ApiModelProperty(notes = "구역명")
  private String sdName;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "회원 아이디")
  private String mbId;

  @ApiModelProperty(notes = "회원 성명")
  private String mbName;

  @ApiModelProperty(notes = "공종A명")
  private String workSectionNameA;

  @ApiModelProperty(notes = "공종B명")
  private String workSectionNameB;

  @ApiModelProperty(notes = "접근 시간")
  private Date slrDatetime;

  @ApiModelProperty(notes = "장비 아이콘 URL")
  private String equipmentIconUrl;
}
