package kr.co.hulan.aas.mvc.api.device.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.HulanComponentSiteType;
import kr.co.hulan.aas.common.code.MacAddressType;
import kr.co.hulan.aas.common.validator.EnumCodeValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateWorkDeviceInfoRequest", description="현장 디바이스 정보 생성 요청")
public class CreateWorkDeviceInfoRequest {

    @NotBlank(message = "현장 아이디는 필수입니다.")
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotNull(message = "장비구분은 필수입니다.")
    @ApiModelProperty(notes = "장비구분", required = true)
    private Integer deviceType;

    @NotBlank(message = "디바이스 식별자는 필수입니다.")
    @ApiModelProperty(notes = "디바이스 식별자", required = true)
    private String deviceId;

    @EnumCodeValid(target = MacAddressType.class, message = "통신방식이 올바르지 않습니다.")
    @NotNull(message = "통신방식은 필수입니다.")
    @ApiModelProperty(notes = "통신방식. 1: 일반, 2: LoRa", required = true)
    private Integer macAddressType;

    @NotBlank(message = "Mac 주소는 필수입니다.")
    @ApiModelProperty(notes = "mac address. ex) FA:16:3E:3C:7C:9A", required = true)
    private String macAddress;

    @ApiModelProperty(notes = "체감온도 사용여부. 0: 미사용, 1: 사용. 디바이스 구분이 풍속계일때 사용")
    private Integer useSensoryTemp;

    @ApiModelProperty(notes = "연관 디바이스. 예) 체감온도 사용시에는 온습도계 지정")
    private Integer refDevice;

}
