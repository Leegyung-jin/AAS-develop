package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="SensorDistrictDto", description="센서 구역 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorDistrictDto {

    @ApiModelProperty(notes = "구역 아이디")
    private Integer sdIdx;
    @ApiModelProperty(notes = "구역명")
    private String sdName;
    private String sdMemo = "";
    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    @ApiModelProperty(notes = "구역표시 기본 색상")
    private String defaultColor;
    @ApiModelProperty(notes = "구역표시 점멸 색상")
    private String flashColor;
    // derived
    @ApiModelProperty(notes = "현장명")
    private String wpName;
    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "센서 설치수")
    private Long sensorCount;

}
