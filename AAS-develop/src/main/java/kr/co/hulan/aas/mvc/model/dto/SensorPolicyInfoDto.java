package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="SensorPolicyInfoDto", description="안전센서 정책 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorPolicyInfoDto {

    private Integer spIdx;
    private String siType;
    private Integer minorMin;
    private Integer minorMax;

    @ApiModelProperty(notes = "스캔 주기", required = true)
    private Integer scanInterval;
    private Integer idleInterval;
    private Integer reportInterval;
    private String wpId;
    private String wpName;
    private java.util.Date createDate;
    private java.util.Date updateDate;
    private String mbId;

    //derived
    private String ccId;
    private String ccName;
}
