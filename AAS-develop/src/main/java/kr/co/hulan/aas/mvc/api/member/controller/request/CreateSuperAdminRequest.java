package kr.co.hulan.aas.mvc.api.member.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateSuperAdminRequest {


    @NotEmpty(message = "회원 아이디가 있어야 합니다.")
    @ApiModelProperty(notes = "회원 아이디", required = true)
    private String mbId;
    @NotEmpty(message = "회원 성명이 있어야 합니다.")
    @ApiModelProperty(notes = "회원 성명", required = true)
    private String name;
    @NotEmpty(message = "회원 패스워드가 있어야 합니다.")
    @ApiModelProperty(notes = "회원 패스워드", required = true)
    private String mbPassword;

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

    @NotEmpty(message = "전화번호가 있어야 합니다.")
    @ApiModelProperty(notes = "전화번호", required = true)
    private String telephone;

    @NotNull(message = "메모 있어야 합니다.")
    @ApiModelProperty(notes = "메모")
    private String mbMemo;

    @NotNull(message = "탈퇴일 있어야 합니다.")
    @Size(max=8)
    @ApiModelProperty(notes = "탈퇴일")
    private String withdrawDate;

    @NotNull(message = "차단일 있어야 합니다.")
    @Size(max=8)
    @ApiModelProperty(notes = "차단일")
    private String blockDate;
}
