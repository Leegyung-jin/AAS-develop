package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosMagneticOpeningStateVo", description="IMOS 개구부 상태 정보")
public class ImosMagneticOpeningStateVo {

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

  @ApiModelProperty(notes = "개구부 상태. 0: 담힘, 1: 열림")
  private Integer status;

  @ApiModelProperty(notes = "측정시간")
  private Date measureTime ;

  @ApiModelProperty(notes = "위험 팝업 여부. 0: 표시안함, 1: 해지시까지 표시함")
  private Integer dashboardPopup;

}
