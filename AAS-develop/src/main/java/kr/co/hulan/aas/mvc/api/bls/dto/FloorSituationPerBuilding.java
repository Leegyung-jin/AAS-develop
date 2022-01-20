package kr.co.hulan.aas.mvc.api.bls.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@ApiModel(value="FloorSituationPerBuilding", description="현장 빌딩 내 층별 상황 정보")
@AllArgsConstructor
@NoArgsConstructor
public class FloorSituationPerBuilding {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "최고 층수")
  private Integer floorUpstair;

  @ApiModelProperty(notes = "최저 층수")
  private Integer floorDownstair;

  @ApiModelProperty(notes = "단면도 파일")
  private UploadedFile crossSectionFile;

  @ApiModelProperty(notes = "단면도 다운로드 URL")
  private String crossSectionFileDownloadUrl;


  @ApiModelProperty(notes = "층별 상황")
  List<FloorSituation> floorSituationList;


  @JsonIgnore
  public void addFloorSituation(FloorSituation situation){
    if( floorSituationList == null ){
      floorSituationList = new ArrayList<FloorSituation>();
    }
    floorSituationList.add(situation);
  }

  @ApiModelProperty(notes = "총 층수")
  public Integer getTotalFloor(){
    if( floorUpstair != null
        && floorDownstair != null
        && floorUpstair >=  floorDownstair
    ){
      if( floorUpstair > 0 ){
        return floorDownstair > 0 ?
            floorUpstair - floorDownstair + 1 :
            floorDownstair < 0 ?
                floorUpstair - floorDownstair : floorUpstair;
      }
      else if( floorUpstair == 0 ){
        return -floorDownstair;
      }
      else {
        return Math.abs( floorUpstair - floorDownstair ) + 1;
      }
    }
    return null;
  }

}
