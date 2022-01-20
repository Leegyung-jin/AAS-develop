package kr.co.hulan.aas.common.model.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@ApiModel(value="DefaultPageResult", description="검색(리스트) 결과")
public class DefaultPageResult<T> {

  @ApiModelProperty(notes = "조회 페이지")
  private int currentPage;
  @ApiModelProperty(notes = "페이지 사이즈")
  private int pageSize;

  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "리스트")
  private List<T> list;

}
