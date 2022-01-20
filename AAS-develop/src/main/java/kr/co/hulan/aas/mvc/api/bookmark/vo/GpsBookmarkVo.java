package kr.co.hulan.aas.mvc.api.bookmark.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="GpsBookmarkVo", description="GPS(fence) Bookmark 정보")
@AllArgsConstructor
@NoArgsConstructor
public class GpsBookmarkVo {

  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장 fence 순번")
  private Integer wpSeq;
  @ApiModelProperty(notes = "현장 fence명")
  private String fenceName;
}
