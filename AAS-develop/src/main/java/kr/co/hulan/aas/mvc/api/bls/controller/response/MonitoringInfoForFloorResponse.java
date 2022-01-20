package kr.co.hulan.aas.mvc.api.bls.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorGridSituation;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorGridSituationPerFloor;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MonitoringInfoForFloorResponse", description="BLS monitoring 빌딩 층별 Grid 상황 정보 응답")
public class MonitoringInfoForFloorResponse {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "지역 타입. 1: 빌딩, 2: 지상, 3: 지하")
  private Integer areaType;

  @ApiModelProperty(notes = "디폴트 층도면 파일 이미지 링크")
  private String imageUrl;

  @ApiModelProperty(notes = "단면도 파일 이미지 링크")
  private String crossSectionFileDownloadUrl;

  @ApiModelProperty(notes = "층 내 Grid 상황")
  List<FloorGridSituationPerFloor> floorGridSituations;
}
