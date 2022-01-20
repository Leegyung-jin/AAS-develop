package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class TrackerAssignLogDto {

  @ApiModelProperty(notes = "트랙커 할당 로그 번호")
  private Long talNo;
  @ApiModelProperty(notes = "트랙커 아이디")
  private String trackerId;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "할당일")
  private java.util.Date assignDate;
  @ApiModelProperty(notes = "생성자 아이디")
  private String assigner;
  @ApiModelProperty(notes = "회수일")
  private java.util.Date collectDate;
  @ApiModelProperty(notes = "생성자 아이디")
  private String collector;


}
