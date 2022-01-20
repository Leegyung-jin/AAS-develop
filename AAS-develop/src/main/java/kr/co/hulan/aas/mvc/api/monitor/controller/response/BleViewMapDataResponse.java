package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.bls.dto.BuildingSituation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="BleViewMapDataResponse", description="[안전모니터4.1] BLE 조감도 Data 응답")
public class BleViewMapDataResponse {

  @ApiModelProperty(notes = "출력인원")
  private Integer totalWorkerCount;

  @ApiModelProperty(notes = "현재인원")
  private Integer currentWorkerCount;

  @ApiModelProperty(notes = "현재 조감도 이미지 URL")
  private String imageUrl;

  @ApiModelProperty(notes = "빌딩별 상황")
  private List<BuildingSituation> buildingSituationList;


}
