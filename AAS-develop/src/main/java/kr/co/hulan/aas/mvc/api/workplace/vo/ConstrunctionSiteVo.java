package kr.co.hulan.aas.mvc.api.workplace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@ApiModel(value="ConstrunctionSiteVo", description="건설사 현장 편입 정보")
@AllArgsConstructor
@NoArgsConstructor
public class ConstrunctionSiteVo {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "건설사명")
  private String ccName;

  @ApiModelProperty(notes = "노동자수")
  private Long workerCount;
  @ApiModelProperty(notes = "당일 노동자 수")
  private Long workerTodayCount;
  @ApiModelProperty(notes = "당월 노동자 수")
  private Long workerMonthCount;
  @ApiModelProperty(notes = "전체 노동자 수")
  private Long workerTotalCount;

  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;


  @Autowired
  private List<ConstructionSiteManagerVo> managerList;


}
