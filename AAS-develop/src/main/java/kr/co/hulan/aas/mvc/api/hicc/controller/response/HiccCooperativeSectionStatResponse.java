package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.SectionStatVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccCooperativeSectionStatResponse", description="공종별 협력사 풀 응답")
public class HiccCooperativeSectionStatResponse {

  @ApiModelProperty(notes = "공종별 협력사 풀 리스트")
  private List<SectionStatVo> sectionList;

  @ApiModelProperty(notes = "리스트 수")
  public long getCount(){
    if( sectionList != null && sectionList.size() > 0 ){
      return sectionList.size();
    }
    return 0L;
  }

}
