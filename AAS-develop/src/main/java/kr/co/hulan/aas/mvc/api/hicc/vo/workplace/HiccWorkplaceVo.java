package kr.co.hulan.aas.mvc.api.hicc.vo.workplace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccWorkplaceVo", description="현장 정보")
public class HiccWorkplaceVo {


  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명(공사명)")
  private String wpName;
  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;
  @ApiModelProperty(notes = "건설사명")
  private String ccName;

  @ApiModelProperty(notes = "착공일")
  private java.util.Date wpStartDate;

  @ApiModelProperty(notes = "준공(예정)일")
  private Date wpEndDate;

  @JsonProperty(access = Access.WRITE_ONLY)
  @ApiModelProperty(notes = "조감도 파일명", hidden = true)
  private String viewMapFileName;
  @JsonProperty(access = Access.WRITE_ONLY)
  @ApiModelProperty(notes = "조감도 원본 파일명", hidden = true)
  private String viewMapFileNameOrg;
  @JsonProperty(access = Access.WRITE_ONLY)
  @ApiModelProperty(notes = "조감도 저장 위치(상대경로)", hidden = true)
  private String viewMapFilePath;
  @JsonProperty(access = Access.WRITE_ONLY)
  @ApiModelProperty(notes = "조감도 저장소. 0: local Storage ", hidden = true)
  private Integer viewMapFileLocation;

  @ApiModelProperty(notes = "기본 주소")
  private String address;
  @ApiModelProperty(notes = "주소 상세")
  private String wpAddr;

  @ApiModelProperty(notes = "무재해 시작일")
  private java.util.Date uninjuryRecordDate;

  @ApiModelProperty(notes = "공사 규모")
  private String  constructScale;

  @ApiModelProperty(notes = "무재해 일수")
  public Long getUninjuryRecordPeriod(){
    if( uninjuryRecordDate != null ){
      LocalDate uninjury = Instant.ofEpochMilli(uninjuryRecordDate.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
      LocalDate now = LocalDate.now();
      return ChronoUnit.DAYS.between(uninjury, now);
    }
    return 0L;
  }

  @ApiModelProperty(notes = "공사진척도(%)")
  public Long getElapsedWork(){
    LocalDate now = LocalDate.now();
    if( wpStartDate != null && wpEndDate != null ){
      LocalDate startLocalDate = Instant.ofEpochMilli(wpStartDate.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
      long elapsedDays = ChronoUnit.DAYS.between(startLocalDate, now);
      if( elapsedDays > 0 ){
        LocalDate endLocalDate = Instant.ofEpochMilli(wpEndDate.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        long totalDays = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
        if( totalDays <= 0 ){
          return 100L;
        }
        return Math.min( elapsedDays * 100 /totalDays, 100L);
      }
    }
    return 0L;
  }


  @ApiModelProperty(notes = "조감도 파일 링크 URL")
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


  public static void main(String args[]) throws Exception{
    /*
    Date date = DateUtils.parseDate("202109141400", new String[] { "yyyyMMddHHmm" });
    System.out.println(date);

    Date start = Date.from(Instant.ofEpochMilli(date.getTime())
        .atZone(ZoneId.systemDefault())
        .toInstant());

    System.out.println(start);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
    Date parsed = formatter.parse("202109141400");
    System.out.println(parsed);




    Date startdDate = new Date(1602514800000L);
    LocalDate start = Instant.ofEpochMilli(startdDate.getTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDate();

    Date endDate = new Date(1611154800000L);
    LocalDate end = Instant.ofEpochMilli(endDate.getTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDate();

    HiccWorkplaceVo vo = new HiccWorkplaceVo();
    vo.setWpStartDate(startdDate);
    vo.setWpEndDate(endDate);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    System.out.println(String.format("%s ~ %s", start.format(formatter), end.format(formatter)));
    System.out.println(String.format("경과일 : %s", vo.getElapsedWork()));

    LocalDate now = LocalDate.now();
    System.out.println(ChronoUnit.DAYS.between(start, now));
    System.out.println(ChronoUnit.DAYS.between(start, end));
    System.out.println(ChronoUnit.DAYS.between(now, end));

     */

  }
}
