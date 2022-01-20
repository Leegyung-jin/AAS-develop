package kr.co.hulan.aas.mvc.api.orderoffice.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.LinkBtnInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OrderingOfficeUpdateRequest", description="발주사 수정 요청")
public class OrderingOfficeUpdateRequest {

  @NotBlank(message = "발주사명은 필수입니다.")
  @ApiModelProperty(notes = "발주사명", required = true)
  private String officeName;
  @NotBlank(message = "전화번호는 필수입니다.")
  @ApiModelProperty(notes = "전화번호", required = true)
  private String telephone;
  @NotBlank(message = "사업자번호는 필수입니다.")
  @ApiModelProperty(notes = "사업자번호", required = true)
  private String biznum;

  @ApiModelProperty(notes = "우편번호")
  private String zonecode;
  @ApiModelProperty(notes = "법정동코드")
  private String bcode;
  @ApiModelProperty(notes = "기본주소")
  private String address;
  @ApiModelProperty(notes = "주소상세")
  private String addressDetail;
  @ApiModelProperty(notes = "시도명")
  private String sido;
  @ApiModelProperty(notes = "시군구명")
  private String sigungu;
  @ApiModelProperty(notes = "법정동명")
  private String bname;

  @ApiModelProperty(notes = "통합관제 화면 타이틀명")
  private String hiccName;

  @ApiModelProperty(notes = "통합관제 타이틀 아이콘 파일")
  private UploadedFile iconFile;

  @ApiModelProperty(notes = "통합관제 화면 background 이미지 파일")
  private UploadedFile bgImgFile;

  @ApiModelProperty(notes = "통합관제 화면 background color")
  private String bgColor;

  @ApiModelProperty(notes = "통합관제 메인 Component 링크 버튼 리스트")
  private List<@Valid LinkBtnInfoDto> mainBtnList;
}
