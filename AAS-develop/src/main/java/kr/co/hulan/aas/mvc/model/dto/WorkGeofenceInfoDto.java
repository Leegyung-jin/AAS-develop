package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkGeofenceInfoDto", description="Geo-Fence 좌표 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkGeofenceInfoDto {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "geo fence 종류. 0: Main, 그외 Sub")
  private Integer wpSeq;

  @ApiModelProperty(notes = "geo fence drawing 순서")
  private Integer seq;

  @NotNull(message="경도는 필수입니다")
  @ApiModelProperty(notes = "경도")
  private Double latitude;

  @NotNull(message="위도는 필수입니다")
  @ApiModelProperty(notes = "위도")
  private Double longitude;

}
