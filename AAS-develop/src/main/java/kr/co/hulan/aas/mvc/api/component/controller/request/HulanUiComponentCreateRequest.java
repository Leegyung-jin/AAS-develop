package kr.co.hulan.aas.mvc.api.component.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.HulanComponentSiteType;
import kr.co.hulan.aas.common.code.HulanComponentUiType;
import kr.co.hulan.aas.common.validator.EnumCodeValid;
import kr.co.hulan.aas.mvc.api.component.vo.HulanUiComponentLevelVo;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HulanUiComponentCreateRequest", description="현장 모니터링 UI 정보 등록 요청")
public class HulanUiComponentCreateRequest {

  @Size(min = 2, max = 16, message = "컴포넌트 아이디는 2~16 자까지 지원합니다.")
  @NotBlank(message = "컴포넌트 아이디는 필수입니다.")
  @ApiModelProperty(notes = "컴포넌트 아이디", required = true)
  private String hcmptId;

  @Size(min = 2, max = 32, message = "컴포넌트명은 2~32 자까지 지원합니다.")
  @NotBlank(message = "컴포넌트명은 필수입니다.")
  @ApiModelProperty(notes = "컴포넌트명", required = true)
  private String hcmptName;

  @ApiModelProperty(notes = "설명")
  private String description;

  @EnumCodeValid(target = HulanComponentSiteType.class, message = "사이트 유형이 올바르지 않습니다.")
  @NotNull(message = "사이트 유형은 필수입니다.")
  @ApiModelProperty(notes = "사이트 유형. 1: IMOS, 2: HICC(통합관제)", required = true)
  private Integer siteType;

  @EnumCodeValid(target = HulanComponentUiType.class, message = "UI 유형이 올바르지 않습니다.")
  @NotNull(message = "UI 유형은 필수입니다.")
  @ApiModelProperty(notes = "UI 유형. 1: 메인화면 유형, 2: 컴포넌트 유형", required = true)
  private Integer uiType;

  @NotNull(message = "컴포넌트의 너비는 필수입니다.")
  @Min(value=1, message = "컴포넌트의 너비는 1이상 4이하여야 합니다.")
  @Max(value=4, message = "컴포넌트의 너비는 1이상 4이하여야 합니다.")
  @ApiModelProperty(notes = "컴포넌트의 너비(x 길이)", required = true)
  private Integer width;

  @NotNull(message = "컴포넌트의 높이는 필수입니다.")
  @Min(value=1, message = "컴포넌트의 높이는 1이상 3이하여야 합니다.")
  @Max(value=3, message = "컴포넌트의 높이는 1이상 3이하여야 합니다.")
  @ApiModelProperty(notes = "컴포넌트의 높이(y 길이)", required = true)
  private Integer height;

  @EnumCodeValid(target = EnableCode.class, message = "상태값이 올바르지 않습니다.")
  @NotNull(message = "상태는 필수입니다.")
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용", required = true)
  private Integer status;

  @NotNull(message = "대표파일은 필수입니다")
  @ApiModelProperty(notes = "대표 파일", required = true)
  private UploadedFile representativeFile;

  @ApiModelProperty(notes = "지원 계정 레벨(등급) 리스트")
  private List<Integer> mbLevelList;
}
