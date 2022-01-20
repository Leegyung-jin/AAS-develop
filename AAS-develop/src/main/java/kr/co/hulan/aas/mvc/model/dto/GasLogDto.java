package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="GasLogDto", description="유해물질 측정 정보")
@AllArgsConstructor
@NoArgsConstructor
public class GasLogDto {

  private Integer idx;
  private String deviceId;
  private String macAddress;
  private Date measureTime;
  private String measureDeviceId;
  private BigDecimal temperature;
  private BigDecimal humidity;
  private BigDecimal o2;
  private BigDecimal h2s;
  private BigDecimal co;
  private BigDecimal ch4;
  private BigDecimal battery;
  private Integer temperatureLevel;
  private Integer o2Level;
  private Integer h2sLevel;
  private Integer coLevel;
  private Integer ch4Level;
  private String hazardPhrase;
  private Integer dashboardPopup;
  private String wpId;
  private String wpName;

}
