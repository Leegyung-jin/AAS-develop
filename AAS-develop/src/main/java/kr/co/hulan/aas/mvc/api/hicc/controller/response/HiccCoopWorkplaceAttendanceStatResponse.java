package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceAttendanceStatVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CooperativeWorkplaceAttendanceStatResponse", description="협력사 현장별 출역 현황 리스트(페이징) 응답")
public class HiccCoopWorkplaceAttendanceStatResponse {

  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "협력사명")
  private String coopMbName;

  @ApiModelProperty(notes = "조회 페이지")
  private int currentPage;
  @ApiModelProperty(notes = "페이지 사이즈")
  private int pageSize;
  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "현장 출역 현황 리스트")
  private List<HiccWorkplaceAttendanceStatVo> list;
}
