package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosIntelliVixEventExportRequest", description="지능형 CCTV 이벤트 Data export 요청")
public class ImosIntelliVixEventExportRequest extends ConditionRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, nvrName: NVR 명, gname: 채널명")
  private String searchName = "COMPLEX";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "지능형 CCTV 채널 아이디")
  private String gid;

  @ApiModelProperty(notes = "지능형 CCTV 이벤트 타입")
  private String type;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  @ApiModelProperty(notes = "검색 시작일. ms 의 long 값 내지는 yyyyMMdd 포맷의 문자")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  @ApiModelProperty(notes = "검색 종료일. ms 의 long 값 내지는 yyyyMMdd 포맷의 문자")
  private Date endDate;

  @ApiModelProperty(notes = "조치여부. 0: 미조치, 1: 조치완료")
  private Integer actionStatus;

  @JsonIgnore
  @NotNull(message = "검색 시작/종료일은 필수입니다.")
  @Max(value = 59, message = "검색 범위는 60일 이내여야 합니다")
  @ApiModelProperty(notes = "검색 범위 체크", hidden = true)
  public Long getDatePeriod(){
    if( startDate != null && endDate != null ){
      LocalDate start = Instant.ofEpochMilli(startDate.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
      LocalDate end = Instant.ofEpochMilli(endDate.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
      long between = ChronoUnit.DAYS.between(start, end);
      return between;
    }
    return null;
  }


  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    if( StringUtils.isNotBlank(gid) ){
      condition.put("gid", gid);
    }
    if( StringUtils.isNotBlank(type) ){
      condition.put("type", type);
    }

    if( startDate != null ){
      condition.put("startDate", startDate);
    }
    if( endDate != null ){
      condition.put("endDate", endDate);
    }
    if( actionStatus != null ){
      condition.put("actionStatus", actionStatus);
    }
    return condition;
  }
}
