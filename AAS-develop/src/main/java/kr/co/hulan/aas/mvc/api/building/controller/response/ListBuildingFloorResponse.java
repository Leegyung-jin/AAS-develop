package kr.co.hulan.aas.mvc.api.building.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.BuildingFloorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListBuildingFloorResponse", description="빌딩 층 정보 검색 응답")
public class ListBuildingFloorResponse {

    @ApiModelProperty(notes = "전체 수")
    private long totalCount;
    @ApiModelProperty(notes = "빌딩 정보 리스트")
    private List<BuildingFloorDto> list;
}
