package kr.co.hulan.aas.mvc.api.monitor.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="GpsObjectVo", description="GPS 대상 정보")
public class GpsObjectVo {

  @ApiModelProperty(notes="데이터 키")
  private String dataKey;
  @ApiModelProperty(notes="데이터 표시명")
  private String dataName;
  @ApiModelProperty(notes="데이터발신 타입. worker : 근로자 단말, device : GPS 기기")
  private String dataType;
  @ApiModelProperty(notes="표시 아이콘 Url 정보")
  private String iconUrl;
  @ApiModelProperty(notes="GPS 정보")
  private GpsLocationVo location;
  @ApiModelProperty(notes="GPS 발송 소유자(기기) 정보")
  private GpsSenderVo owner;
  @ApiModelProperty(notes="장비 정보")
  private EquiptmentVo equipment;
  @ApiModelProperty(notes="마킹 여부. 0: Disable, 1: Enable")
  private Integer marker;

}
