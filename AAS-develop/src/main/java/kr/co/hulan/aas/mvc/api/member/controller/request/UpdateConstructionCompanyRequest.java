package kr.co.hulan.aas.mvc.api.member.controller.request;

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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UpdateConstructionCompanyRequest", description="건설사 정보 수정 요청")
public class UpdateConstructionCompanyRequest {

    @NotBlank(message = "건설사 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "건설사 아이디", required = true)
    private String ccId;

    @NotBlank(message = "건설사명이 있어야 합니다.")
    @ApiModelProperty(notes = "건설사명", required = true)
    private String ccName;

    @NotBlank(message = "사업자등록번호가 있어야 합니다.")
    @ApiModelProperty(notes = "사업자등록번호", required = true)
    private String ccNum;

    @ApiModelProperty(notes = "우편번호1")
    private String ccZip1;

    @ApiModelProperty(notes = "우편번호2")
    private String ccZip2;

    @ApiModelProperty(notes = "주소")
    private String ccAddr1;

    @ApiModelProperty(notes = "상세주소")
    private String ccAddr2;

    @ApiModelProperty(notes = "상세주소2")
    private String ccAddr3;

    @ApiModelProperty(notes = "지번/도로명 구분자")
    private String ccAddrJibeon;

    @NotBlank(message = "전화번호가 있어야 합니다.")
    @ApiModelProperty(notes = "전화번호", required = true)
    private String ccTel;

    @ApiModelProperty(notes = "메모")
    private String ccMemo;

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
