package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.NvrEventActionMethod;
import kr.co.hulan.aas.common.code.NvrEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NvrEventDto", description="NVR 이벤트 정보")
public class NvrEventDto {

  @ApiModelProperty(notes = "이벤트 관리번호")
  private Long eventNo;
  @ApiModelProperty(notes = "이벤트 아이디")
  private String elogId;
  @ApiModelProperty(notes = "시작 이벤트 로그 넘버")
  private Long startElogNo;
  @ApiModelProperty(notes = "이벤트 발생일")
  private java.util.Date startTm;
  @ApiModelProperty(notes = "종료 이벤트 로그 넘버")
  private Long endElogNo;
  @ApiModelProperty(notes = "이벤트 종료일")
  private java.util.Date endTm;
  @ApiModelProperty(notes = "이벤트 유지시간 (second)")
  private Long eventDuration;
  @ApiModelProperty(notes = "NVR 관리번호")
  private Long nvrNo;
  @ApiModelProperty(notes = "NVR명")
  private String nvrName;
  @ApiModelProperty(notes = "관련 아이디(이벤트 유형에 따라 의미하는 바가 다르다)")
  private Long id;
  @ApiModelProperty(notes = "이벤트 유형. 8: 연기, 9: 불꽃, 10: 쓰러짐, 26: 안전모 미착용 등 ")
  private String type;
  @ApiModelProperty(notes = "이벤트 상태. 1: 시작, 2: 계속, 4: 종료")
  private String stat;
  @ApiModelProperty(notes = "보행자속성")
  private String pattr;
  @ApiModelProperty(notes = "이벤트 존 이름")
  private String ezn;
  @ApiModelProperty(notes = "NVR 채널 아이디")
  private String gid;
  @ApiModelProperty(notes = "채널명")
  private String gname;
  @ApiModelProperty(notes = "조치여부. 0: 미조치, 1: 조치완료")
  private Integer actionStatus;
  @ApiModelProperty(notes = "조치방법. 1: 확인 완료, 2: 감지오류")
  private Integer actionMethod;
  @ApiModelProperty(notes = "조치완료 처리일")
  private java.util.Date actionEndDate;
  @ApiModelProperty(notes = "조치완료 처리자")
  private String actionEndTreator;
  @ApiModelProperty(notes = "메모")
  private String memo;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "등록일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "등록자")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자")
  private String updater;

  @ApiModelProperty(notes = "이벤트 유형명")
  public String getTypeName(){
    NvrEventType etype = NvrEventType.get(type);
    return etype != null ? etype.getExposeName() : "";
  }

  @ApiModelProperty(notes = "조치방법명")
  public String getActionMethodName(){
    NvrEventActionMethod method = NvrEventActionMethod.get(actionMethod);
    return method != null ? method.getName() : "";
  }
}
