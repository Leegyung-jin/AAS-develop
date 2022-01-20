package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorSituations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="BleCrossSectionDataResponse", description="[안전모니터4.1] BLE 단면도 Data 응답")
public class BleCrossSectionDataResponse {

  @ApiModelProperty(notes = "출력인원")
  private Integer totalWorkerCount;

  @ApiModelProperty(notes = "현재인원")
  private Integer currentWorkerCount;

  @ApiModelProperty(notes = "현재 단면도 이미지 URL")
  private String imageUrl;

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "층별 상황")
  private List<FloorSituations> floorSituationList;
}
