package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.TiltLogDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosTiltDeviceStateVo", description="기울기 센서 정보")
public class ImosTiltDeviceStateVo {

  @ApiModelProperty(notes = "디바이스 넘버")
  private Integer idx;
  @ApiModelProperty(notes = "디바이스 아이디")
  private String deviceId;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "mac address")
  private String macAddress;

  @ApiModelProperty(notes = "노출명")
  private String displayName;
  @ApiModelProperty(notes = "기울기 X")
  private Double tiltX;
  @ApiModelProperty(notes = "기울기 Y")
  private Double tiltY;
  @ApiModelProperty(notes = "기울기 Z")
  private Double tiltZ;
  @ApiModelProperty(notes = "측정시간")
  private Date measureTime ;

  @ApiModelProperty(notes = "위험 팝업 여부")
  private Integer dashboardPopup;

  @ApiModelProperty(notes = "기울기 수치 이력 리스트")
  private List<TiltLogDto> tiltLogHistory;


}
