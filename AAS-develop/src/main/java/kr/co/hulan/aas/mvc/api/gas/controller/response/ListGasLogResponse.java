package kr.co.hulan.aas.mvc.api.gas.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.GasLogDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListGasLogResponse", description="유해 물질 측정 수치 검색 요청")
public class ListGasLogResponse   {

  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "현장 장비 정보 리스트")
  private List<GasLogDto> list;
}
