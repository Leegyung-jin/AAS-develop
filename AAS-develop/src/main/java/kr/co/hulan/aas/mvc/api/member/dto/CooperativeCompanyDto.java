package kr.co.hulan.aas.mvc.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="CooperativeCompanyDto", description="협력사 정보")
@AllArgsConstructor
@NoArgsConstructor
public class CooperativeCompanyDto {

    @ApiModelProperty(notes = "회원 일련번호")
    private Long mbNo;
    @ApiModelProperty(notes = "회원 아이디")
    private String mbId;
    @ApiModelProperty(notes = "회원 성명")
    private String name;
    @ApiModelProperty(notes = "공종A")
    private String workSectionA;
    @ApiModelProperty(notes = "공종A명")
    private String workSectionNameA;

    @ApiModelProperty(notes = "우편번호1")
    private String mbZip1;
    @ApiModelProperty(notes = "우편번호2")
    private String mbZip2;
    @ApiModelProperty(notes = "주소")
    private String mbAddr1;
    @ApiModelProperty(notes = "상세주소")
    private String mbAddr2;
    @ApiModelProperty(notes = "참고항목")
    private String mbAddr3;
    @ApiModelProperty(notes = "지번/도로명 구분자 [ R:도로명, J:지번 ]")
    private String mbAddrJibeon;
    @ApiModelProperty(notes = "전화번호")
    private String telephone;
    @ApiModelProperty(notes = "가입일")
    private java.util.Date registDate;
    @ApiModelProperty(notes = "최종접속시간")
    private java.util.Date latestLogin;

    @ApiModelProperty(notes = "일반푸시 [ 1:ON, 2:OFF ]", required = true)
    private String pushNormal;
    @ApiModelProperty(notes = "위험푸시(사이렌) [ 1:ON, 2:OFF ]", required = true)
    private String pushDanger;
    @ApiModelProperty(notes = "메모")
    private String mbMemo;

    @ApiModelProperty(notes = "탈퇴일")
    private String withdrawDate;

    @ApiModelProperty(notes = "차단일")
    private String blockDate;

    @ApiModelProperty(notes = "계정 잠김 여부")
    private boolean isLocked;
    @ApiModelProperty(notes = "패스워드 만료 여부")
    private boolean isPasswordExpired;
}
