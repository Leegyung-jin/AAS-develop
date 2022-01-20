package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.DeviceType;
import kr.co.hulan.aas.common.code.MacAddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkDeviceInfoDto", description="현장 디바이스 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkDeviceInfoDto {

    @ApiModelProperty(notes = "디바이스 번호")
    private Integer idx;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "현장명")
    private String wpName;

    @ApiModelProperty(notes = "장비 구분")
    private Integer deviceType;

    @ApiModelProperty(notes = "디바이스 식별자")
    private String deviceId;

    @ApiModelProperty(notes = "통신방식. 1: 일반, 2: LoRa")
    private Integer macAddressType;

    @ApiModelProperty(notes = "mac address. ex) FA:16:3E:3C:7C:9A")
    private String macAddress;

    @ApiModelProperty(notes = "체감온도 사용여부. 0: 미사용, 1: 사용. 디바이스 구분이 풍속계일때 사용")
    private Integer useSensoryTemp;

    @ApiModelProperty(notes = "연관 디바이스. 예) 체감온도 사용시에는 온습도계 지정")
    private Integer refDevice;

    @ApiModelProperty(notes = "등록/변경 일시")
    private java.util.Date updateDatetime;

    @ApiModelProperty(notes = "등록/변경자")
    private String updater;

    @ApiModelProperty(notes = "장비 구분명")
    public String getDeviceTypeName(){
        if( deviceType != null ){
            DeviceType dtype = DeviceType.get(deviceType);
            if( dtype != null );{
                return dtype.getName();
            }
        }
        return "";
    }

    @ApiModelProperty(notes = "통신방식명")
    public String getMacAddressTypeName(){
        if( macAddressType != null ){
            MacAddressType type = MacAddressType.get(macAddressType);
            if( type != null );{
                return type.getName();
            }
        }
        return "";
    }
}
