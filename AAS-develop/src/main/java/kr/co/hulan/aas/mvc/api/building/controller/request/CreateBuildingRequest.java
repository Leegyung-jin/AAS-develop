package kr.co.hulan.aas.mvc.api.building.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.BuildingAreaType;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateBuildingRequest", description="빌딩 정보 생성 요청")
public class CreateBuildingRequest {

  @NotEmpty
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;
  @NotEmpty
  @Length(min = 2, max = 64)
  @ApiModelProperty(notes = "빌딩명", required = true)
  private String buildingName;

  @NotNull
  @ApiModelProperty(notes = "지역 타입. 1: 빌딩, 2: 지상, 3: 지하", required = true)
  private Integer areaType;

  @ApiModelProperty(notes = "옥상 포함 여부. 0: 없음, 1: 있음'")
  private Integer containRoof;

  @ApiModelProperty(notes = "갱폼 포함 여부. 0: 없음, 1: 있음")
  private Integer containGangform;

  @Max(value = 100, message = "지상 100 층 이하여야 합니다.")
  @Min(value = -10, message = "지하 10층 이상이어야 합니다")
  @ApiModelProperty(notes = "최고 층수. 없으면 디폴트 0", required = true)
  private Integer floorUpstair;

  @Max(value = 100, message = "지상 100 층 이하여야 합니다.")
  @Min(value = -10, message = "지하 10층 이상이어야 합니다")
  @ApiModelProperty(notes = "최저 층수.", required = true)
  private Integer floorDownstair;

  @NotNull
  @ApiModelProperty(notes = "건물 위치 X 좌표", required = true)
  private Integer posX;

  @NotNull
  @ApiModelProperty(notes = "건물 위치 Y 좌표", required = true)
  private Integer posY;

  @NotNull
  @ApiModelProperty(notes = "상황판 위치 X 좌표", required = true)
  private Integer boxX;

  @NotNull
  @ApiModelProperty(notes = "상황판 위치 Y 좌표", required = true)
  private Integer boxY;

  @NotNull
  @ApiModelProperty(notes = "디폴트 층도면 파일", required = true)
  private UploadedFile viewFloorFile;

  @ApiModelProperty(notes = "단면도 파일")
  private UploadedFile crossSectionFile;

  @Min(value = 1, message = "총 층수는 최소 1이상이어야 합니다.")
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

  @Min(value = 1, message = "최대/최소 층수는 0 이 될 수 없습니다.")
  @ApiModelProperty(notes = "최대/최소 층수 체크")
  public Integer getFloorCheck(){
    if( floorUpstair == 0 || floorDownstair == 0  ){
      return 0;
    }
    return 1;
  }

  public void init(){
    if( containRoof == null ){
      containRoof = 0;
    }
    if( containGangform == null ){
      containGangform = 0;
    }
    if( areaType == null || areaType != BuildingAreaType.BUILDING.getCode() ){
      floorDownstair = floorUpstair;
    }
  }
}
