package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import kr.co.hulan.aas.common.utils.YamlPropertiesAccessor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="G5MemberDto", description="멤버 정보")
@AllArgsConstructor
@NoArgsConstructor
public class G5MemberDto {

    @JsonIgnoreProperties
    public static String SAFETY_EDUCATION_FILE_DOWNLOAD_URL =
            YamlPropertiesAccessor.getMessage("file.repository.download")
            +YamlPropertiesAccessor.getMessage("file.repository.safeFilePath");

    @ApiModelProperty(notes = "회원 일련번호")
    private Long mbNo;
    @ApiModelProperty(notes = "회원 아이디")
    private String mbId = "";
    @ApiModelProperty(notes = "패스워드")
    private String mbPassword = "";
    @ApiModelProperty(notes = "회원 성명")
    private String name = "";
    @ApiModelProperty(notes = "최종접속시간")
    private java.util.Date latestLogin;
    @ApiModelProperty(notes = "닉네임")
    private String mbNick = "";
    @ApiModelProperty(notes = "닉네임 등록일")
    private java.util.Date mbNickDate;
    @ApiModelProperty(notes = "이메일")
    private String mbEmail = "";
    @ApiModelProperty(notes = "홈페이지")
    private String mbHomepage = "";
    @ApiModelProperty(notes = "등급")
    private Integer mbLevel = 0;
    @ApiModelProperty(notes = "성별")
    private String mbSex  = "";
    @ApiModelProperty(notes = "생년월일")
    private String birthday = "";
    @ApiModelProperty(notes = "전화번호")
    private String telephone = "";
    @ApiModelProperty(notes = "휴대폰번호")
    private String mbHp = "";
    private String mbCertify = "";
    private Integer mbAdult = 0;
    private String mbDupinfo = "";
    @ApiModelProperty(notes = "우편번호1")
    private String mbZip1 = "";
    @ApiModelProperty(notes = "우편번호2")
    private String mbZip2 = "";
    @ApiModelProperty(notes = "주소")
    private String mbAddr1 = "";
    @ApiModelProperty(notes = "상세주소")
    private String mbAddr2 = "";
    @ApiModelProperty(notes = "상세주소2")
    private String mbAddr3 = "";
    @ApiModelProperty(notes = "지번/도로명 구분자")
    private String mbAddrJibeon = "";
    private String mbSignature;
    private String mbRecommend = "";
    private Long mbPoint = 0L;
    private String mbLoginIp = "";
    @ApiModelProperty(notes = "가입일")
    private java.util.Date registDate;
    private String mbIp = "";
    @ApiModelProperty(notes = "탈퇴일")
    private String withdrawDate = "";
    @ApiModelProperty(notes = "차단일")
    private String blockDate = "";
    private java.util.Date mbEmailCertify;
    private String mbEmailCertify2 = "";
    private String mbMemo;
    private String mbLostCertify = "";
    private Integer mbMailling = 0;
    private Integer mbSms = 0;
    private Integer mbOpen = 0;
    private java.util.Date mbOpenDate;
    private String mbProfile  = "";
    private String mbMemoCall  = "";

    @ApiModelProperty(notes = "근로자 번호")
    private String memberShipNo  = "";
    @ApiModelProperty(notes = "가입현장")
    private String workPlace = "";

    private String mb3 = "";
    private String mb4 = "";
    @ApiModelProperty(notes = "공종A")
    private String workSectionA  = "";
    @ApiModelProperty(notes = "공종A명")
    private String workSectionNameA  = "";
    @ApiModelProperty(notes = "혈액형")
    private String bloodType = "";
    private String mb6 = "";
    private String mb7 = "";
    private String mb8 = "";
    private String mb9 = "";
    @ApiModelProperty(notes = "안전교육이수파일")
    private String safetyEducationFile = "";
    private String mb11 = "";
    private String mb12 = "";
    private String mb13 = "";
    private String mb14 = "";
    private String mb15 = "";
    private String mb16 = "";
    private String mb17 = "";
    @ApiModelProperty(notes = "일반푸시 [ 1:ON, 2:OFF ]")
    private String pushNormal = "";
    @ApiModelProperty(notes = "위험푸시(사이렌) [ 1:ON, 2:OFF ]")
    private String pushDanger = "";
    @ApiModelProperty(notes = "디바이스 아이디")
    private String deviceId = "";
    @ApiModelProperty(notes = "앱 버전")
    private String appVersion;
    @ApiModelProperty(notes = "패스워드 변경일")
    private Date pwdChangeDate;
    @ApiModelProperty(notes = "로그인 시도수(실패수)")
    private Integer attemptLoginCount;

    // Derived

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId  = "";
    @ApiModelProperty(notes = "건설사명")
    private String ccName = "";
    @ApiModelProperty(notes = "현장명")
    private String wpName = "";
    @ApiModelProperty(notes = "가입업체(협력사)명")
    private String regCoopMbName = "";

    @ApiModelProperty(notes = "OTP 전화번호 리스트")
    private List<MemberOtpPhoneDto> otpPhoneList;

    @ApiModelProperty(notes = "안전교육이수파일 다운로드 URL")
    public String getSafetyEducationFileDownloadUrl(){
        if(StringUtils.isNotBlank(safetyEducationFile)){
            return SAFETY_EDUCATION_FILE_DOWNLOAD_URL + safetyEducationFile;
        }
        return "";
    }

    @JsonIgnore
    public boolean isAllowedPushDanger(){
        return StringUtils.equals(pushDanger, "1");
    }

    @ApiModelProperty(notes = "노티 타입. App 버전 > '3.2.6' 여부 ")
    public Integer getNotiType(){
        return StringUtils.isNotEmpty(appVersion)
                && StringUtils.compare(appVersion, "3.2.6") > 0
                ? 1 : 0 ;
    }



}
