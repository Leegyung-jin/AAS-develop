package kr.co.hulan.aas.mvc.api.gps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EquipmentInfo", description="장비 정보")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentInfo {

    @ApiModelProperty(notes = "장비 유형")
    private Integer equipmentType;

    @ApiModelProperty(notes = "장비이름")
    private String equipmentName;

    @ApiModelProperty(notes = "장비 icon url")
    private String equipmentIcon;

    @ApiModelProperty(notes = "차량 번호")
    private String equipmentNo;

    @ApiModelProperty(notes = "고정 GPS 모델")
    private String deviceModel;

    @ApiModelProperty(notes = "고정 GPS 단말 mac_address")
    private String macAddress;
}
