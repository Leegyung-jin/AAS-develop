package kr.co.hulan.aas.mvc.api.workplace.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.hulan.aas.mvc.api.workplace.vo.ConstructionSiteManagerVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ConstructionSiteCreateRequest", description="건설사 현장 편입 요청")
public class ConstructionSiteCreateRequest {

  @NotBlank(message = "현장 아이디는 필수입니다.")
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @NotBlank(message = "건설사 아이디는 필수입니다.")
  @ApiModelProperty(notes = "건설사 아이디", required = true)
  private String ccId;

  @NotNull(message = "매니저는 필수입니다.")
  @Size(min=1, message = "매니저는 최소 한명을 할당하여야 합니다.")
  @ApiModelProperty(notes = "매니저 아이디 리스트", required = true)
  private List<String> managerMbIdList;

}
