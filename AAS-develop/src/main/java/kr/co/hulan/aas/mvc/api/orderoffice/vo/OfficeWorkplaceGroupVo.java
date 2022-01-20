package kr.co.hulan.aas.mvc.api.orderoffice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeGroupManagerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OfficeWorkplaceGroupVo", description="발주사 현장그룹 정보")
public class OfficeWorkplaceGroupVo {

  @ApiModelProperty(notes = "발주사 현장그룹 번호")
  private Long wpGrpNo;
  @ApiModelProperty(notes = "발주사 번호")
  private Long officeNo;
  @ApiModelProperty(notes = "발주사명")
  private String officeName;
  @ApiModelProperty(notes = "현장그룹명")
  private String officeGrpName;

  @ApiModelProperty(notes = "현장수")
  private Integer workplaceCount;

  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "최종수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "최종수정자")
  private String updater;

  @ApiModelProperty(notes = "현장그룹 매니저 리스트")
  private List<OfficeGroupManagerDto> managerList;
}
