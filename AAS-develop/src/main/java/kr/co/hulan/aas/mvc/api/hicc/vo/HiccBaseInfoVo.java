package kr.co.hulan.aas.mvc.api.hicc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSidoVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.LinkBtnInfoDto;
import kr.co.hulan.aas.mvc.model.dto.HiccMainBtnDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccBaseInfoVo", description="통합 관제 기본 정보")
public class HiccBaseInfoVo {

  @ApiModelProperty(notes = "통합 관제 타이틀")
  private String title;
  @ApiModelProperty(notes = "통합 관제 ICON URL")
  private String iconUrl;
  @ApiModelProperty(notes = "통합 관제 백그라운드색")
  private String bgColor;
  @ApiModelProperty(notes = "통합 관제 백그라운드 이미지 URL")
  private String bgImgUrl;
  @ApiModelProperty(notes = "통합 관제 메인 버튼 리스트")
  private List<LinkBtnInfoDto> btnList;

}
