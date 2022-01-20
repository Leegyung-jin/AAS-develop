package kr.co.hulan.aas.mvc.api.monitor.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EnvironmentMeasureDeviceVo", description="유해물질 측정 디바이스 정보")
public class EnvironmentMeasureDeviceVo {

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
  @ApiModelProperty(notes = "battery 잔량(%)")
  private BigDecimal battery;
  @ApiModelProperty(notes = "위험 팝업 여부")
  private Integer dashboardPopup;
  @ApiModelProperty(notes = "위험 문구")
  private String hazardPhrase;
  @ApiModelProperty(notes = "측정시간")
  private Date measureTime ;

  @ApiModelProperty(notes = "유해물질 측정 리스트")
  private List<EnvironmentMeasureVo> list;
}
