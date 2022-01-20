package kr.co.hulan.aas.mvc.api.member.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="UpdateWorkerRequest", description="근로자 정보 수정 요청")
public class UpdateWorkerRequest {

    @NotEmpty(message = "회원 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "회원 아이디 (근로자:휴대폰번호)", required = true)
    private String mbId;
    @NotEmpty(message = "회원 성명이 있어야 합니다.")
    @ApiModelProperty(notes = "회원 성명", required = true)
    private String name;

    @ApiModelProperty(notes = "회원 패스워드")
    private String mbPassword;

    @NotNull(message = "생년월일이 있어야 합니다.")
    @ApiModelProperty(notes = "생년월일. YYMMDD 형식. 예) 1970년01월01일은 700101")
    private String birthday;

    @NotNull(message = "혈액형이 있어야 합니다.")
    @ApiModelProperty(notes = "혈액형")
    private String bloodType;

    @NotNull(message = "우편번호가 있어야 합니다.")
    @ApiModelProperty(notes = "우편번호1")
    private String mbZip1;
    @NotNull(message = "우편번호가 있어야 합니다.")
    @ApiModelProperty(notes = "우편번호2")
    private String mbZip2;
    @NotNull(message = "기본주소 있어야 합니다.")
    @ApiModelProperty(notes = "기본주소")
    private String mbAddr1;
    @NotNull(message = "상세주소 있어야 합니다.")
    @ApiModelProperty(notes = "상세주소")
    private String mbAddr2;
    @NotNull(message = "참고항목 있어야 합니다.")
    @ApiModelProperty(notes = "참고항목")
    private String mbAddr3;
    @NotNull(message = "지번/도로명 구분자 있어야 합니다.")
    @ApiModelProperty(notes = "지번/도로명 구분자 [ R:도로명, J:지번 ]")
    private String mbAddrJibeon;

    /*
    @NotNull(message = "가입현장 있어야 합니다.")
    @ApiModelProperty(notes = "가입현장")
    private String workPlace;

    @ApiModelProperty(notes = "가입업체(협력사) 아이디")
    private String regCoopMbId;
     */

    @NotEmpty(message = "일반푸시 여부 있어야 합니다.")
    @ApiModelProperty(notes = "일반푸시 [ 1:ON, 2:OFF ]", required = true)
    private String pushNormal;

    @NotEmpty(message = "위험푸시 여부 있어야 합니다.")
    @ApiModelProperty(notes = "위험푸시(사이렌) [ 1:ON, 2:OFF ]", required = true)
    private String pushDanger;

    @ApiModelProperty(notes = "업로드 안전교육이수파일. 추가/변경시에만 정보 존재해야함.")
    private String safetyEducationFile;

    @NotNull(message = "메모 있어야 합니다.")
    @ApiModelProperty(notes = "메모")
    private String mbMemo;

    @NotNull(message = "탈퇴일 있어야 합니다.")
    @ApiModelProperty(notes = "탈퇴일")
    private String withdrawDate;

    @NotNull(message = "차단일 있어야 합니다.")
    @ApiModelProperty(notes = "차단일")
    private String blockDate;
}
