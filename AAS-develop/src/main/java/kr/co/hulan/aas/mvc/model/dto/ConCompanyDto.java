package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.LinkBtnInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="ConCompanyDto", description="건설사 정보")
@AllArgsConstructor
@NoArgsConstructor
public class ConCompanyDto {

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "사업자등록번호")
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
    @ApiModelProperty(notes = "전화번호")
    private String ccTel;
    @ApiModelProperty(notes = "메모")
    private String ccMemo;

    @ApiModelProperty(notes = "통합관제 관리번호")
    private Long hiccNo;
    @ApiModelProperty(notes = "통합관제명")
    private String hiccName;

    @JsonProperty(access= Access.WRITE_ONLY)
    @ApiModelProperty(notes = "아이콘 파일명", hidden = true)
    private String iconFileName;
    @JsonProperty(access= Access.WRITE_ONLY)
    @ApiModelProperty(notes = "아이콘 원본 파일명", hidden = true)
    private String iconFileNameOrg;
    @JsonProperty(access= Access.WRITE_ONLY)
    @ApiModelProperty(notes = "아이콘 저장 위치(상대경로)", hidden = true)
    private String iconFilePath;
    @JsonProperty(access= Access.WRITE_ONLY)
    @ApiModelProperty(notes = "아이콘 저장소. 0: local Storage ", hidden = true)
    private Integer iconFileLocation;

    @JsonProperty(access= Access.WRITE_ONLY)
    @ApiModelProperty(notes = "백그라운드 이미지 파일명", hidden = true)
    private String bgImgFileName;
    @JsonProperty(access= Access.WRITE_ONLY)
    @ApiModelProperty(notes = "백그라운드 이미지 원본 파일명", hidden = true)
    private String bgImgFileNameOrg;
    @JsonProperty(access= Access.WRITE_ONLY)
    @ApiModelProperty(notes = "백그라운드 이미지 저장 위치(상대경로)", hidden = true)
    private String bgImgFilePath;
    @JsonProperty(access= Access.WRITE_ONLY)
    @ApiModelProperty(notes = "백그라운드 이미지 저장소. 0: local Storage ", hidden = true)
    private Integer bgImgFileLocation;

    @ApiModelProperty(notes = "백그라운드 색")
    private String bgColor;

    @ApiModelProperty(notes = "메인 버튼 리스트")
    private List<LinkBtnInfoDto> mainBtnList;

    @ApiModelProperty(notes = "등록일")
    private Date ccDatetime;



    @JsonIgnore
    public void createCcId(){
        this.ccId = GenerateIdUtils.getUuidKey();
    }

    @ApiModelProperty(notes = "Icon 파일")
    public UploadedFile getIconFile(){
        if(StringUtils.isNotEmpty(iconFileName)
            && StringUtils.isNotEmpty(iconFilePath)
            && iconFileLocation != null ){
            Storage storage = Storage.get(iconFileLocation);
            if( storage != null ){
                return new UploadedFile(
                    iconFileName, iconFileNameOrg
                );
            }
        }
        return null;
    }

    @ApiModelProperty(notes = "Icon Url")
    public String getIconUrl(){
        if(StringUtils.isNotEmpty(iconFileName)
            && StringUtils.isNotEmpty(iconFilePath)
            && iconFileLocation != null ){
            Storage storage = Storage.get(iconFileLocation);
            if( storage != null ){
                StringBuilder sb = new StringBuilder();
                sb.append(storage.getDownloadUrlBase());
                sb.append(iconFilePath);
                sb.append(iconFileName);
                return sb.toString();
            }
        }
        return "";
    }

    @ApiModelProperty(notes = "background 이미지 파일")
    public UploadedFile getBgImgFile(){
        if(StringUtils.isNotEmpty(bgImgFileName)
            && StringUtils.isNotEmpty(bgImgFilePath)
            && bgImgFileLocation != null ){
            Storage storage = Storage.get(bgImgFileLocation);
            if( storage != null ){
                return new UploadedFile(
                    bgImgFileName, bgImgFileNameOrg
                );
            }
        }
        return null;
    }

    @ApiModelProperty(notes = "백그라운드 이미지 Url")
    public String getBgImgUrl(){
        if(StringUtils.isNotEmpty(bgImgFileName)
            && StringUtils.isNotEmpty(bgImgFilePath)
            && bgImgFileLocation != null ){
            Storage storage = Storage.get(bgImgFileLocation);
            if( storage != null ){
                StringBuilder sb = new StringBuilder();
                sb.append(storage.getDownloadUrlBase());
                sb.append(bgImgFilePath);
                sb.append(bgImgFileName);
                return sb.toString();
            }
        }
        return "";
    }
}
