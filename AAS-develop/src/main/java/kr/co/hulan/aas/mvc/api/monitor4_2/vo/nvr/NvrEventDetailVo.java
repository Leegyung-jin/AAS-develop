package kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.common.code.NvrEventActionMethod;
import kr.co.hulan.aas.common.code.NvrEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NvrEventDetailVo", description="NVR 이벤트 상세 정보")
public class NvrEventDetailVo {

  @ApiModelProperty(notes = "이벤트 관리번호")
  private Long eventNo;
  @ApiModelProperty(notes = "이벤트 발생일")
  private java.util.Date startTm;
  @ApiModelProperty(notes = "이벤트 종료일")
  private java.util.Date endTm;
  @ApiModelProperty(notes = "이벤트 유지시간 (second)")
  private Long eventDuration;
  @ApiModelProperty(notes = "NVR 관리번호")
  private Long nvrNo;
  @ApiModelProperty(notes = "NVR명")
  private String nvrName;
  @ApiModelProperty(notes = "NVR 채널 아이디")
  private String gid;
  @ApiModelProperty(notes = "채널명")
  private String gname;
  @ApiModelProperty(notes = "이벤트 유형. 8: 화재위험, 10: 근로자 쓰러짐, 26: 안전모 미착용 등 ")
  private String type;
  @ApiModelProperty(notes = "이벤트 상태. 1: 시작, 2: 계속, 4: 종료")
  private String stat;
  @ApiModelProperty(notes = "메모")
  private String memo;
  @ApiModelProperty(notes = "조치여부. 0: 미조치, 1: 조치완료")
  private Integer actionStatus;
  @ApiModelProperty(notes = "조치방법. 1: 확인 완료, 2: 감지오류")
  private Integer actionMethod;
  @ApiModelProperty(notes = "조치완료 시간")
  private java.util.Date actionEndDate;
  @ApiModelProperty(notes = "조치완료자")
  private String actionEndTreator;

  @ApiModelProperty(notes = "이벤트 파일 리스트", hidden = true)
  List<NvrEventFileVo> eventFileList;

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

  @ApiModelProperty(notes = "이벤트 대표 파일 URL")
  public String getEventFileDownloadUrl(){
    if( eventFileList != null && eventFileList.size() > 0 ){
      return eventFileList.get(0).getFileDownloadUrl();
    }
    return "";
  }
}
