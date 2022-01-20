package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Basic;
import javax.persistence.Column;

@Data
@ApiModel(value="BuildingFloorDto", description="현장 빌딩 층 정보")
@AllArgsConstructor
@NoArgsConstructor
public class BuildingFloorDto {

    @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
    private Long buildingNo;
    @ApiModelProperty(notes = "층")
    private Integer floor;
    @ApiModelProperty(notes = "층명")
    private String floorName;
    @ApiModelProperty(notes = "층타입. 1: 층, 1000: 옥상,  2000: 갱폼")
    private Integer floorType;
    @ApiModelProperty(notes = "층도면 파일명")
    private String viewFloorFileName;
    @ApiModelProperty(notes = "층도면 원본 파일명")
    private String viewFloorFileNameOrg;
    @ApiModelProperty(notes = "층도면 저장 위치(상대경로)")
    private String viewFloorFilePath;
    @ApiModelProperty(notes = "층도면 저장소. 0: local Storage ")
    private Integer viewFloorFileLocation;

    @ApiModelProperty(notes = "단면도 층 표시 x 축 좌표 ")
    private Integer crossSectionGridX;
    @ApiModelProperty(notes = "단면도 층 표시 y 축 좌표")
    private Integer crossSectionGridY;
    @ApiModelProperty(notes = "단면도 층/구획 정보 표시 x 축 좌표 ")
    private Integer boxX;
    @ApiModelProperty(notes = "단면도 층/구획 정보 표시 y 축 좌표")
    private Integer boxY;


    @ApiModelProperty(notes = "작업진행 여부. 0: 미진행, 1: 진행")
    private Integer activated;

    @ApiModelProperty(notes = "수정일")
    private java.util.Date updateDate;
    @ApiModelProperty(notes = "수정자")
    private String updater;

    // Derived Info
    @ApiModelProperty(notes = "현장아이디")
    private String wpId;
    @ApiModelProperty(notes = "현장명")
    private String wpName;
    @ApiModelProperty(notes = "빌딩명")
    private String buildingName;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "설치된 센서 수")
    private Integer sensorCount;

    @ApiModelProperty(notes = "디폴트 층도면 파일명")
    private String defaultViewFloorFileName;
    @ApiModelProperty(notes = "디폴트 층도면 원본 파일명")
    private String defaultViewFloorFileNameOrg;
    @ApiModelProperty(notes = "디폴트 층도면 저장 위치(상대경로)")
    private String defaultViewFloorFilePath;
    @ApiModelProperty(notes = "디폴트 층도면 저장소. 0: local Storage ")
    private Integer defaultViewFloorFileLocation;

    @ApiModelProperty(notes = "단면도 파일명")
    private String crossSectionFileName;
    @ApiModelProperty(notes = "단면도 원본 파일명")
    private String crossSectionFileNameOrg;
    @ApiModelProperty(notes = "단면도 저장 위치(상대경로)")
    private String crossSectionFilePath;
    @ApiModelProperty(notes = "단면도 저장소. 0: local Storage ")
    private Integer crossSectionFileLocation;

    public UploadedFile getViewFloorFile(){
        if(StringUtils.isNotEmpty(viewFloorFileName)
                && StringUtils.isNotEmpty(viewFloorFilePath)
                && viewFloorFileLocation != null ){
            Storage storage = Storage.get(viewFloorFileLocation);
            if( storage != null ){
                return new UploadedFile(
                        viewFloorFileName, viewFloorFileNameOrg
                );
            }
        }
        return null;
    }

    public String getViewFloorFileDownloadUrl(){
        if(StringUtils.isNotEmpty(viewFloorFileName)
                && StringUtils.isNotEmpty(viewFloorFilePath)
                && viewFloorFileLocation != null ){
            Storage storage = Storage.get(viewFloorFileLocation);
            if( storage != null ){
                StringBuilder sb = new StringBuilder();
                sb.append(storage.getDownloadUrlBase());
                sb.append(viewFloorFilePath);
                sb.append(viewFloorFileName);
                return sb.toString();
            }
        }
        return "";
    }


    public UploadedFile getDefaultViewFloorFile(){
        if(StringUtils.isNotEmpty(defaultViewFloorFileName)
                && StringUtils.isNotEmpty(defaultViewFloorFilePath)
                && defaultViewFloorFileLocation != null ){
            Storage storage = Storage.get(defaultViewFloorFileLocation);
            if( storage != null ){
                return new UploadedFile(
                        defaultViewFloorFileName, defaultViewFloorFileNameOrg
                );
            }
        }
        return null;
    }

    public String getDefaultViewFloorFileDownloadUrl(){
        if(StringUtils.isNotEmpty(defaultViewFloorFileName)
                && StringUtils.isNotEmpty(defaultViewFloorFilePath)
                && defaultViewFloorFileLocation != null ){
            Storage storage = Storage.get(defaultViewFloorFileLocation);
            if( storage != null ){
                StringBuilder sb = new StringBuilder();
                sb.append(storage.getDownloadUrlBase());
                sb.append(defaultViewFloorFilePath);
                sb.append(defaultViewFloorFileName);
                return sb.toString();
            }
        }
        return "";
    }


    public UploadedFile getCrossSectionFile(){
        if(StringUtils.isNotEmpty(crossSectionFileName)
                && StringUtils.isNotEmpty(crossSectionFilePath)
                && crossSectionFileLocation != null ){
            Storage storage = Storage.get(crossSectionFileLocation);
            if( storage != null ){
                return new UploadedFile(
                        crossSectionFileName, crossSectionFileNameOrg
                );
            }
        }
        return null;
    }

    public String getCrossSectionFileDownloadUrl(){
        if(StringUtils.isNotEmpty(crossSectionFileName)
                && StringUtils.isNotEmpty(crossSectionFilePath)
                && crossSectionFileLocation != null ){
            Storage storage = Storage.get(crossSectionFileLocation);
            if( storage != null ){
                StringBuilder sb = new StringBuilder();
                sb.append(storage.getDownloadUrlBase());
                sb.append(crossSectionFilePath);
                sb.append(crossSectionFileName);
                return sb.toString();
            }
        }
        return "";
    }
}
