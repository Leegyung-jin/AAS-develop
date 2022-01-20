package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkPlaceMonitorConfigDto", description="스마트 안전모니터 설정 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceMonitorConfigDto {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "GPS 현재인원 표시여부. 1: ON")
  private Integer currentWorkerGps;
  @ApiModelProperty(notes = "BLE 현재인원 표시여부. 1: ON")
  private Integer currentWorkerBle;
  @ApiModelProperty(notes = "먼지 수치 표시여부. 1: ON")
  private Integer environmentDust;
  @ApiModelProperty(notes = "소음 수치 표시여부. 1: ON")
  private Integer environmentNoise;
  @ApiModelProperty(notes = "위험물질 수치 표시여부. 1: ON")
  private Integer environmentGas;
  @ApiModelProperty(notes = "낙하이벤트. 1: ON")
  private Integer fallingEvent;
  @ApiModelProperty(notes = "칼만필터 적용. 1: ON")
  private Integer kalmanFilter;
  @ApiModelProperty(notes = "NVR 이벤트 수신. 1: ON")
  private Integer nvrEvent;

  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "최종 수정자")
  private String updater;

  @JsonIgnore
  public void init(){
    if(currentWorkerGps == null ){
      currentWorkerGps = 0;
    }
    if(currentWorkerBle == null ){
      currentWorkerBle = 0;
    }
    if(environmentDust == null ){
      environmentDust = 0;
    }
    if(environmentNoise == null ){
      environmentNoise = 0;
    }
    if(environmentGas == null ){
      environmentGas = 0;
    }
    if(createDate == null ){
      createDate = new Date();
    }
    if(updateDate == null ){
      updateDate = new Date();
    }
  }
}
