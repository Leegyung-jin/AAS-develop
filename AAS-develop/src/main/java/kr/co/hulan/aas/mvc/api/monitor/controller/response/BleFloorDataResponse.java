package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorGridSituation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="BleFloorDataResponse", description="[안전모니터4.1] BLE 평면도 Data 응답")
public class BleFloorDataResponse {

  @ApiModelProperty(notes = "출력인원")
  private Integer totalWorkerCount;

  @ApiModelProperty(notes = "현재인원")
  private Integer currentWorkerCount;

  @ApiModelProperty(notes = "현재 평면도 이미지 URL")
  private String imageUrl;

  @ApiModelProperty(notes = "총 층수")
  public Integer totalFloor;

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "빌딩 층명")
  private String floorName;

  @ApiModelProperty(notes = "지역 타입. 1: 빌딩, 2: 지상, 3: 지하")
  private Integer areaType;

  @ApiModelProperty(notes = "층이 1개뿐인 경우 해당 층 넘버")
  private Integer oneStoriedFloor;

  @ApiModelProperty(notes = "센서별 상황")
  private List<FloorGridSituation> floorSensorSituationList;

}
