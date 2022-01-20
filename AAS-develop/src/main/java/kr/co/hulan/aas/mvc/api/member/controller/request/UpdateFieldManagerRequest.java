package kr.co.hulan.aas.mvc.api.member.controller.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import kr.co.hulan.aas.mvc.model.dto.MemberOtpPhoneDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateFieldManagerRequest {

    @NotEmpty(message = "건설사 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "건설사 아이디", required = true)
    private String ccId;

    @NotEmpty(message = "회원 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "회원 아이디 (근로자:휴대폰번호)", required = true)
    private String mbId;

    @NotEmpty(message = "회원 성명이 있어야 합니다.")
    @ApiModelProperty(notes = "회원 성명", required = true)
    private String name;

    @ApiModelProperty(notes = "회원 패스워드")
    private String mbPassword;

    @NotEmpty(message = "전화번호가 있어야 합니다.")
    @ApiModelProperty(notes = "전화번호", required = true)
    private String telephone;

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

    @NotEmpty(message = "일반푸시 여부 있어야 합니다.")
    @ApiModelProperty(notes = "일반푸시 [ 1:ON, 2:OFF ]", required = true)
    private String pushNormal;

    @NotEmpty(message = "위험푸시 여부 있어야 합니다.")
    @ApiModelProperty(notes = "위험푸시(사이렌) [ 1:ON, 2:OFF ]", required = true)
    private String pushDanger;

    @NotNull(message = "메모 있어야 합니다.")
    @ApiModelProperty(notes = "메모")
    private String mbMemo;

    @NotNull(message = "탈퇴일 있어야 합니다.")
    @ApiModelProperty(notes = "탈퇴일")
    private String withdrawDate;

    @NotNull(message = "차단일 있어야 합니다.")
    @ApiModelProperty(notes = "차단일")
    private String blockDate;

    @ApiModelProperty(notes = "OTP 전화번호 리스트")
    private List<@Valid MemberOtpPhoneDto> otpPhoneList;
}
