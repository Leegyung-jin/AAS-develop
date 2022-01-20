package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.AdminMsgAlarmGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="AdminMsgInfoDto", description="현장 관리자 알림 메시지 정보")
@AllArgsConstructor
@NoArgsConstructor
public class AdminMsgInfoDto {

  @ApiModelProperty(notes = "알림 넘버(SEQ)")
  private Integer idx;
  @ApiModelProperty(notes = "사용자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "메세지 알림 유형. 100: 위험지역 접근 알림, 120: 앱 기능 해제 알림.")
  private Integer msgType;

  @ApiModelProperty(notes = "메세지 위험 등급. 1: 주의, 2: 경계, 3: 심각")
  private Integer alarmGrade;

  @ApiModelProperty(notes = "알림 제목")
  private String subject;
  @ApiModelProperty(notes = "알림 내용")
  private String msg;
  @ApiModelProperty(notes = "push 발송 여부. 0 : 발송,    1 : 미발송")
  private Integer isSend;
  @ApiModelProperty(notes = "생성/수정 시간")
  private java.util.Date uptDatetime;

  @ApiModelProperty(notes = "메세지 위험 등급명")
  public String getAlarmGradeName(){
    AdminMsgAlarmGrade gradeEnum = AdminMsgAlarmGrade.get(alarmGrade);
    return gradeEnum != null ? gradeEnum.getName() : "";
  }



}
