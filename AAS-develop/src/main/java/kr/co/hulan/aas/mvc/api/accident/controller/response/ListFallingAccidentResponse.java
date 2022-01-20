package kr.co.hulan.aas.mvc.api.accident.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.FallingAccidentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListFallingAccidentResponse", description="낙하 이벤트 리스트 응답")
public class ListFallingAccidentResponse {

  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "낙하 이벤트 리스트")
  private List<FallingAccidentDto> list;

}
