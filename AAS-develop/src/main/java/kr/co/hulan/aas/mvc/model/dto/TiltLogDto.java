package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="TiltLogDto", description="기울기 센서 로그 정보")
public class TiltLogDto {

  @ApiModelProperty(notes = "mac address")
  private String macAddress;
  @ApiModelProperty(notes = "측정시간")
  private Date measureTime ;
  @ApiModelProperty(notes = "노출명")
  private String displayName;
  @ApiModelProperty(notes = "기울기 X")
  private Double tiltX;
  @ApiModelProperty(notes = "기울기 Y")
  private Double tiltY;
  @ApiModelProperty(notes = "기울기 Z")
  private Double tiltZ;

}
