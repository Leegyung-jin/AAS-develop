package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.monitor.dto.GpsObjectVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="GpsDataResponse", description="[안전모니터4.1] GPS Data 응답")
public class GpsDataResponse {

  @ApiModelProperty(notes = "출력인원")
  private Integer totalWorkerCount;

  @ApiModelProperty(notes = "현재인원")
  private Integer currentWorkerCount;

  @ApiModelProperty(notes="근로자 데이터 리스트")
  private List<GpsObjectVo> workers;
  @ApiModelProperty(notes="장비 운전자 데이터 리스트")
  private List<GpsObjectVo> drivers;
  @ApiModelProperty(notes="장비 데이터 리스트")
  private List<GpsObjectVo> equipments;


}
