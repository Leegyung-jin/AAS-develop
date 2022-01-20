package kr.co.hulan.aas.mvc.api.orderoffice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="LinkBtnInfoDto", description="버튼 정보")
public class LinkBtnInfoDto {

  @NotBlank(message = "버튼명은 필수입니다.")
  @ApiModelProperty(notes = "버튼명", required = true)
  private String hbtnName;
  @NotBlank(message = "버튼 링크 URL은 필수입니다.")
  @ApiModelProperty(notes = "버튼 링크 URL", required = true)
  private String hbtnLinkUrl;

}
