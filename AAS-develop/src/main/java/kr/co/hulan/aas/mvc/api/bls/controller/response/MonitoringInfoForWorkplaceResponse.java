package kr.co.hulan.aas.mvc.api.bls.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.bls.dto.BuildingSituation;
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
@ApiModel(value="MonitoringInfoForWorkplaceResponse", description="BLS monitoring 현장 내 빌딩 상황 정보 응답")
public class MonitoringInfoForWorkplaceResponse {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "조감도 파일 이미지 링크")
  private String imageUrl;
  @ApiModelProperty(notes = "메모")
  private String memo;

  @ApiModelProperty(notes = "CCTV 링크 URL")
  private String linkCctv;
  @ApiModelProperty(notes = "계측기 링크 URL")
  private String linkMeasurement;
  @ApiModelProperty(notes = "웨어러블#1 링크 URL")
  private String linkWearable1;
  @ApiModelProperty(notes = "웨어러블#1 상태값. 0:OFF, 1: ON")
  private Integer linkWearable1Status;
  @ApiModelProperty(notes = "웨어러블#2 링크 URL")
  private String linkWearable2;
  @ApiModelProperty(notes = "웨어러블#2 상태값. 0:OFF, 1: ON")
  private Integer linkWearable2Status;

  @ApiModelProperty(notes = "현장 내 빌딩 상황 정보 리스트")
  List<BuildingSituation> buildingSituationList;


  @ApiModelProperty(notes = "전체 사용자수")
  public int getTotalWorkerCount(){
    int totalCount = 0;
    if( buildingSituationList != null ){
      for(BuildingSituation situ : buildingSituationList){
        totalCount += situ.getWorkerCount();
      }
    }
    return totalCount;
  }


}
