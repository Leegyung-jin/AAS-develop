package kr.co.hulan.aas.mvc.api.component.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.EnableCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="HulanUiComponentVo", description="UI 컴보넌트 상세 정보")
public class HulanUiComponentDetailVo extends HulanUiComponentVo {

  @ApiModelProperty(notes = "지원 계정 레벨(등급) 전체 선택 리스트")
  private List<HulanUiComponentLevelSelectVo> selectableMbLevelList;

  @ApiModelProperty(notes = "지원 계정 레벨(등급)리스트")
  public List<HulanUiComponentLevelSelectVo> getSelectedMbLevelList(){
    if( selectableMbLevelList != null && selectableMbLevelList.size() > 0 ){
      return selectableMbLevelList.stream()
          .filter( level -> EnableCode.get(level.getSelected()) == EnableCode.ENABLED )
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  @ApiModelProperty(notes = "지원 계정 레벨(등급) 코드 리스트")
  public List<Integer> getMbLevelList(){
    if( selectableMbLevelList != null && selectableMbLevelList.size() > 0 ){
      return selectableMbLevelList.stream()
          .filter( level -> EnableCode.get(level.getSelected()) == EnableCode.ENABLED )
          .map( level -> level.getMbLevel() )
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

}
