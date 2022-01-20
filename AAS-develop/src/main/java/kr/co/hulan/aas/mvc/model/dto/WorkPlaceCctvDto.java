package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="WorkPlaceCctvDto", description="현장 CCTV 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceCctvDto {

  @ApiModelProperty(notes = "CCTV 관리번호")
  private Long cctvNo;
  @ApiModelProperty(notes = "CCTV 명")
  private String cctvName;
  @ApiModelProperty(notes = "CCTV 유형")
  private Integer cctvKind;
  @ApiModelProperty(notes = "CCTV URL")
  private String cctvUrl;
  @ApiModelProperty(notes = "설명")
  private String description;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용")
  private Integer status;
  @ApiModelProperty(notes = "CCTV PTZ 사용여부. 0: 미사용, 1: 사용")
  private Integer ptzStatus;

  @ApiModelProperty(notes = "IntelliVix 채널 아이디")
  private String gid;

  @ApiModelProperty(notes = "IntelliVix 채널명")
  private String gidName;

  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자")
  private String updater;

  @ApiModelProperty(notes = "CCTV SRC")
  public String getSrc(){
    return "";
  }

}
