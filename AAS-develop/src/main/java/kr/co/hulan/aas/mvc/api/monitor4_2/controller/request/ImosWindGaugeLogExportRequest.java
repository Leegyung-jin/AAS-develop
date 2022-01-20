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
@ApiModel(value="ImosWindGaugeLogExportRequest", description="풍속계 측정 수치 로그 Export 요청")
public class ImosWindGaugeLogExportRequest extends ConditionRequest  {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체")
  private String searchName = "COMPLEX";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  @ApiModelProperty(notes = "검색시작일. yyyyMMdd ")
  private Date startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  @ApiModelProperty(notes = "검색종료일. yyyyMMdd ")
  private Date endDate;

  @ApiModelProperty(notes = "디바이스 관리번호")
  private Integer idx;

  @JsonIgnore
  @NotNull(message = "검색 시작/종료일은 필수입니다.")
  @Max(value = 6, message = "검색 범위는 7일 이내여야 합니다")
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

    if( idx != null ){
      condition.put("idx", idx);
    }
    if( startDate != null ){
      condition.put("startDate", startDate);
    }
    if( endDate != null ){
      condition.put("endDate", endDate);
    }
    return condition;
  }
}
