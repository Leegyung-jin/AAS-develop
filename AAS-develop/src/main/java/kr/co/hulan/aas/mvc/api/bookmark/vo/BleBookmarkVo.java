package kr.co.hulan.aas.mvc.api.bookmark.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="BleBookmarkVo", description="BLE Bookmark 정보")
@AllArgsConstructor
@NoArgsConstructor
public class BleBookmarkVo {

  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "빌딩 넘버")
  private Long buildingNo;
  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;
  @ApiModelProperty(notes = "층 넘버")
  private Integer floor;
  @ApiModelProperty(notes = "층명")
  private String floorName;

}
