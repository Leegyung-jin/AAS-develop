package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="WorkPlaceDto", description="현장 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceDto {

    private Integer wpIdx;
    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    private String wpCate;
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    private String manMbId;
    private String manMbName;
    @ApiModelProperty(notes = "현장명(공사명)")
    private String wpName;
    private String wpSido;
    private String wpGugun;
    private String wpAddr;
    private String wpTel;
    @ApiModelProperty(notes = "착공일")
    private java.util.Date wpStartDate;
    private java.util.Date wpEndDate;
    private java.sql.Time wpEdutimeStart;
    private java.sql.Time wpEdutimeEnd;
    private Integer wpEndStatus;
    private String wpMemo;
    private java.util.Date wpDatetime;
    @ApiModelProperty(notes = "조감도 파일명")
    private String viewMapFileName;
    @ApiModelProperty(notes = "조감도 원본 파일명")
    private String viewMapFileNameOrg;
    @ApiModelProperty(notes = "조감도 저장 위치(상대경로)")
    private String viewMapFilePath;
    @ApiModelProperty(notes = "조감도 저장소. 0: local Storage ")
    private Integer viewMapFileLocation;

    @ApiModelProperty(notes = "무재해 시작일")
    private java.util.Date uninjuryRecordDate;


    @ApiModelProperty(notes = "노동자수")
    private Long workerCount;
    @ApiModelProperty(notes = "당일 노동자 수")
    private Long workerTodayCount;
    @ApiModelProperty(notes = "당월 노동자 수")
    private Long workerMonthCount;
    @ApiModelProperty(notes = "전체 노동자 수")
    private Long workerTotalCount;
    @ApiModelProperty(notes = "센서 설치수")
    private Long sensorCount;
    @ApiModelProperty(notes = "디폴트 협력사 아이디")
    private String defaultCoopMbId;
    @ApiModelProperty(notes = "디폴트 협력사명")
    private String defaultCoopMbName;


    @ApiModelProperty(notes = "CCTV 링크 URL")
    private String linkCctv;
    @ApiModelProperty(notes = "계측기 링크 URL")
    private String linkMeasurement;
    @ApiModelProperty(notes = "웨어러블#1 링크 URL")
    private String linkWearable1;
    @ApiModelProperty(notes = "웨어러블#2 링크 URL")
    private String linkWearable2;
    @ApiModelProperty(notes = "웨어러블#1 상태값. 1: ON")
    private Integer linkWearable1Status;
    @ApiModelProperty(notes = "웨어러블#2 상태값. 1: ON")
    private Integer linkWearable2Status;

    @ApiModelProperty(notes = "GPS 현재인원 표시여부. 1: ON")
    private Integer currentWorkerGps;
    @ApiModelProperty(notes = "BLE 현재인원 표시여부. 1: ON")
    private Integer currentWorkerBle;
    @ApiModelProperty(notes = "먼지 수치 표시여부. 1: ON")
    private Integer environmentDust;
    @ApiModelProperty(notes = "소음 수치 표시여부. 1: ON")
    private Integer environmentNoise;
    @ApiModelProperty(notes = "위험물질 수치 표시여부. 1: ON")
    private Integer environmentGas;
    @ApiModelProperty(notes = "BLE 현장 모니터링 지원여부. 0: 미지원, 1: 지원")
    private Integer supportBle;
    @ApiModelProperty(notes = "GPS 현장 모니터링 지원여부. 0: 미지원, 1: 지원")
    private Integer supportGps;
    @ApiModelProperty(notes = "추락감지 지원 여부. 0: 미지원, 1: 지원")
    private Integer fallingEvent;
    @ApiModelProperty(notes = "칼만 필터 지원 여부. 0: 미지원, 1: 지원")
    private Integer kalmanFilter;

    @ApiModelProperty(notes = "미세먼지 측정소명")
    private String stationName;

    @ApiModelProperty(notes = "Activation Geofence 경도")
    private Double activationGeofenceLongitude;

    @ApiModelProperty(notes = "Activation Geofence 위도")
    private Double activationGeofenceLatitude;

    @ApiModelProperty(notes = "Activation Geofence 반경 (단위 m)")
    private Integer activationGeofenceRadius;

    @ApiModelProperty(notes = "시군구명")
    private String sigungu;

    @ApiModelProperty(notes = "동명")
    private String bname;

    @ApiModelProperty(notes = "주소")
    private String address;

    @JsonProperty("support_3d")
    @ApiModelProperty(notes = "3D 현장 모니터링 지원여부. 0: 미지원, 1: 지원")
    private Integer support3d;

    @ApiModelProperty(notes = "GPS 현장 latitude")
    private Double gpsCenterLat;

    @ApiModelProperty(notes = "GPS 현장 longitude")
    private Double gpsCenterLong;

    @ApiModelProperty(notes = "발주사 관리번호")
    private Long officeNo;

    @ApiModelProperty(notes = "발주사명")
    private String officeName;

    @ApiModelProperty(notes = "공사 규모")
    private String  constructScale;

    public UploadedFile getViewMapFile(){
        if(StringUtils.isNotEmpty(viewMapFileName)
          && StringUtils.isNotEmpty(viewMapFilePath)
          && viewMapFileLocation != null ){
            Storage storage = Storage.get(viewMapFileLocation);
            if( storage != null ){
                return new UploadedFile(
                    viewMapFileName, viewMapFileNameOrg
                );
            }
        }
        return null;
    }

    public String getViewMapFileDownloadUrl(){
        if(StringUtils.isNotEmpty(viewMapFileName)
            && StringUtils.isNotEmpty(viewMapFilePath)
            && viewMapFileLocation != null ){
            Storage storage = Storage.get(viewMapFileLocation);
            if( storage != null ){
                StringBuilder sb = new StringBuilder();
                sb.append(storage.getDownloadUrlBase());
                sb.append(viewMapFilePath);
                sb.append(viewMapFileName);
                return sb.toString();
            }
        }
        return "";
    }


    @JsonIgnore
    public void createId(){
        this.wpId = GenerateIdUtils.getUuidKey();
    }

}
