package kr.co.hulan.aas.mvc.api.device.controller.request.nvr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.NetworkVideoRecoderType;
import kr.co.hulan.aas.common.validator.EnumCodeValid;
import kr.co.hulan.aas.common.validator.IpAddressValid;
import kr.co.hulan.aas.common.validator.PortValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NetworkVideoRecoderCreateRequest", description="NVR 생성 요청")
public class NetworkVideoRecoderCreateRequest {

  @NotBlank(message = "NVR명은 필수입니다.")
  @ApiModelProperty(notes = "NVR명", required = true)
  private String nvrName;

  @NotNull(message = "NVR 유형은 필수입니다.")
  @EnumCodeValid(target = NetworkVideoRecoderType.class, message = "NVR 유형이 올바르지 않습니다.")
  @ApiModelProperty(notes = "NVR 유형", required = true)
  private Integer nvrType;

  @NotBlank(message = "계정 아이디는 필수입니다.")
  @ApiModelProperty(notes = "계정 아이디", required = true)
  private String id;
  @NotBlank(message = "계정 패스워드는 필수입니다.")
  @ApiModelProperty(notes = "계정 패스워드", required = true)
  private String pw;
  @NotBlank(message = "IP는 필수입니다.")
  @IpAddressValid(message = "IP 가 올바르지 않습니다.")
  @ApiModelProperty(notes = "IP", required = true)
  private String ip;
  @NotNull(message = "Port는 필수입니다.")
  @PortValid(message = "Port 가 올바르지 않습니다.")
  @ApiModelProperty(notes = "Port", required = true)
  private Integer port;

  @NotNull(message = "상태는 필수입니다.")
  @EnumCodeValid(target = EnableCode.class, message = "상태값이 올바르지 않습니다.")
  @ApiModelProperty(notes = "상태", required = true)
  private Integer status;

  @NotBlank(message = "현장 아이디는 필수입니다.")
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @ApiModelProperty(notes = "설명")
  private String description;

}
