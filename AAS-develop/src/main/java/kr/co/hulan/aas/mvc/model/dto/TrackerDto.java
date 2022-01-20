package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="TrackerDto", description="트랙커 정보")
@AllArgsConstructor
@NoArgsConstructor
public class TrackerDto {

  @ApiModelProperty(notes = "트랙커 아이디")
  private String trackerId;
  @ApiModelProperty(notes = "디바이스모델명")
  private String deviceModel;
  @ApiModelProperty(notes = "현장")
  private String wpId;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자 아이디")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자 아이디")
  private String updater;

  // Derived
  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;
  @ApiModelProperty(notes = "건설사명")
  private String ccName;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;
  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "근로자명")
  private String mbName;
  @ApiModelProperty(notes = "근로자 번호")
  private String memberShipNo;
  @ApiModelProperty(notes = "할당일")
  private Date assignDate;
  @ApiModelProperty(notes = "할당자 아이디")
  private String assigner;
  @ApiModelProperty(notes = "할당자명")
  private String assignerName;

}
