package kr.co.hulan.aas.mvc.api.component.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateUiComponentRequest", description="현장 모니터링 UI 정보 등록 요청")
public class CreateUiComponentRequest {

  @Size(min = 2, max = 16, message = "컴포넌트 아이디는 2~16 자까지 지원합니다.")
  @NotBlank(message = "컴포넌트 아이디는 필수입니다.")
  @ApiModelProperty(notes = "컴포넌트 아이디", required = true)
  private String cmptId;
  @Size(min = 2, max = 32, message = "컴포넌트명은 2~32 자까지 지원합니다.")
  @NotBlank(message = "컴포넌트명은 필수입니다.")
  @ApiModelProperty(notes = "컴포넌트명", required = true)
  private String cmptName;
  @ApiModelProperty(notes = "설명")
  private String description;

  @NotNull(message = "대표파일은 필수입니다")
  @ApiModelProperty(notes = "대표 파일", required = true)
  private UploadedFile representativeFile;


  @NotNull(message = "상태는 필수입니다.")
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용", required = true)
  private Integer status;

}
