package kr.co.hulan.aas.mvc.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="WorkerDto", description="근로자 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @ApiModelProperty(notes = "회원 일련번호")
    private Long mbNo;
    @ApiModelProperty(notes = "회원 아이디 (근로자:휴대폰번호)")
    private String mbId;
    @ApiModelProperty(notes = "회원 성명")
    private String name;
    @ApiModelProperty(notes = "생년월일")
    private String birthday;
    @ApiModelProperty(notes = "근로자 번호")
    private String memberShipNo;
    @ApiModelProperty(notes = "혈액형")
    private String bloodType;
    @ApiModelProperty(notes = "가입현장")
    private String workPlace;

    @ApiModelProperty(notes = "우편번호1")
    private String mbZip1;
    @ApiModelProperty(notes = "우편번호2")
    private String mbZip2;
    @ApiModelProperty(notes = "기본주소")
    private String mbAddr1;
    @ApiModelProperty(notes = "상세주소")
    private String mbAddr2;
    @ApiModelProperty(notes = "참고항목")
    private String mbAddr3;
    @ApiModelProperty(notes = "지번/도로명 구분자 [ R:도로명, J:지번 ]")
    private String mbAddrJibeon;

    @ApiModelProperty(notes = "메모")
    private String mbMemo;
    @ApiModelProperty(notes = "접속IP")
    private String mbIp;


    @ApiModelProperty(notes = "일반푸시 [ 1:ON, 2:OFF ]")
    private String pushNormal;
    @ApiModelProperty(notes = "위험푸시(사이렌) [ 1:ON, 2:OFF ]")
    private String pushDanger;
    @ApiModelProperty(notes = "안전교육이수파일")
    private String safetyEducationFile;
    @ApiModelProperty(notes = "가입일")
    private java.util.Date registDate;
    @ApiModelProperty(notes = "최종접속시간")
    private java.util.Date latestLogin;
    @ApiModelProperty(notes = "탈퇴일")
    private String withdrawDate;
    @ApiModelProperty(notes = "차단일")
    private String blockDate;

    @ApiModelProperty(notes = "가입업체(협력사) 아이디")
    private String regCoopMbId = "";
    @ApiModelProperty(notes = "가입업체(협력사)명")
    private String regCoopMbName = "";

    @ApiModelProperty(notes = "계정 잠김 여부")
    private boolean isLocked;
    @ApiModelProperty(notes = "패스워드 만료 여부")
    private boolean isPasswordExpired;

    @ApiModelProperty(notes = "안전교육이수파일 다운로드 URL")
    public String getSafetyEducationFileDownloadUrl(){
        if(StringUtils.isNotBlank(safetyEducationFile)){
            return G5MemberDto.SAFETY_EDUCATION_FILE_DOWNLOAD_URL + safetyEducationFile;
        }
        return "";
    }
}
