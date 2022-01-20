package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="SensorBuildingLocationDto", description="센서 빌딩 위치 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorBuildingLocationDto {

  @ApiModelProperty(notes = "센서 아이디")
  private Integer siIdx;
  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;
  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;
  @ApiModelProperty(notes = "층명")
  private String floorName;
  @ApiModelProperty(notes = "x 축 좌표")
  private Integer gridX;
  @ApiModelProperty(notes = "y 축 좌표")
  private Integer gridY;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자 아이디(mb_id)")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자 아이디(mb_id) ")
  private String updater;

  // Derived Info
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "건설사명")
  private String ccName;
  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;
  @ApiModelProperty(notes = "구역명")
  private String sdName;
  @ApiModelProperty(notes = "안전센서번호")
  private String siCode;
  @ApiModelProperty(notes = "안전센서유형")
  private String siType;
  @ApiModelProperty(notes = "위치1")
  private String siPlace1;
  @ApiModelProperty(notes = "위치2")
  private String siPlace2;

  @ApiModelProperty(notes = "기본색")
  private String defaultColor;

  @ApiModelProperty(notes = "점멸색")
  private String flashColor;

  @ApiModelProperty(notes = "층이 1개뿐인 경우 해당 층 넘버")
  private Integer oneStoriedFloor;
}
