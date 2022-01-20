package kr.co.hulan.aas.mvc.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@ApiModel(value="ConstructionCompanyDto", description="건설사 정보")
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionCompanyDto {

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "사업자등록번호")
    private String ccNum;
    @ApiModelProperty(notes = "주소")
    private String ccAddr1;
    @ApiModelProperty(notes = "상세주소")
    private String ccAddr2;
    @ApiModelProperty(notes = "전화번호")
    private String ccTel;
    @ApiModelProperty(notes = "등록일")
    private Date ccDatetime;

    @ApiModelProperty(notes = "현장 수")
    private Long wpCount;
    @ApiModelProperty(notes = "협력사수")
    private Long coopCount;
    @ApiModelProperty(notes = "노동자수")
    private Long workerCount;

    @ApiModelProperty(notes = "아이콘 파일명")
    private String iconFileName;
    @ApiModelProperty(notes = "아이콘 원본 파일명")
    private String iconFileNameOrg;
    @ApiModelProperty(notes = "아이콘 저장 위치(상대경로)")
    private String iconFilePath;
    @ApiModelProperty(notes = "아이콘 저장소. 0: local Storage ")
    private Integer iconFileLocation;

    @ApiModelProperty(notes = "백그라운드 이미지 파일명")
    private String bgImgFileName;
    @ApiModelProperty(notes = "백그라운드 이미지 원본 파일명")
    private String bgImgFileNameOrg;
    @ApiModelProperty(notes = "백그라운드 이미지 저장 위치(상대경로)")
    private String bgImgFilePath;
    @ApiModelProperty(notes = "백그라운드 이미지 저장소. 0: local Storage ")
    private Integer bgImgFileLocation;

    @ApiModelProperty(notes = "백그라운드 이미지 저장소. 0: local Storage ")
    private String bgColor;

}
