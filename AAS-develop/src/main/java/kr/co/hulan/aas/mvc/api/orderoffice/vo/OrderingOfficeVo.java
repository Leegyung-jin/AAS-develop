package kr.co.hulan.aas.mvc.api.orderoffice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.HiccMainBtnDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OrderingOfficeVo", description="발주사 상세 정보")
public class OrderingOfficeVo extends OrderingOfficeListVo {

  @ApiModelProperty(notes = "현장 그룹 리스트")
  private List<OfficeWorkplaceGroupVo> groupList;

  @ApiModelProperty(notes = "메인 버튼 리스트")
  private List<LinkBtnInfoDto> mainBtnList;
}
