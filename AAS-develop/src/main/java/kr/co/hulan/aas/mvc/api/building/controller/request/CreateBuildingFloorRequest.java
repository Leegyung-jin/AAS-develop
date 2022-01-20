package kr.co.hulan.aas.mvc.api.building.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateBuildingFloorRequest", description="층(구획) 정보 추가 요청")
public class CreateBuildingFloorRequest {

  @NotBlank(message="층(구획)명은 필수입니다.")
  @ApiModelProperty(notes = "구획명", required = true)
  private String floorName;

  @NotNull(message="작업진행 여부는 필수입니다.")
  @ApiModelProperty(notes = "구획내 작업 진행여부. 0: 미진행, 1: 진행", required = true)
  private Integer activated;

  @NotNull(message="단면도/구획도 내 위치 x 축 좌표는 필수입니다. ")
  @ApiModelProperty(notes = "구획도 내 위치 x 축 좌표 ", required = true)
  private Integer crossSectionGridX = 0;

  @NotNull(message="단면도/구획도 내 위치 y 축 좌표는 필수입니다. ")
  @ApiModelProperty(notes = "구획도 내 위치 y 축 좌표" , required = true)
  private Integer crossSectionGridY = 0;

  @NotNull(message="단면도/구획도 내 상황판 위치 x 축 좌표는 필수입니다. ")
  @ApiModelProperty(notes = "상황판 위치 X 좌표", required = true)
  private Integer boxX;

  @NotNull(message="단면도/구획도 내 상황판 위치 y 축 좌표는 필수입니다. ")
  @ApiModelProperty(notes = "상황판 위치 Y 좌표", required = true)
  private Integer boxY;

  @ApiModelProperty(notes = "층(구획) 도면 파일. 없으면 삭제 ( 디폴트 구획도 사용 )")
  private UploadedFile viewFloorFile;

}
