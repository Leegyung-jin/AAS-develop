package kr.co.hulan.aas.mvc.api.gas.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListGasLogRequest", description="유해 물질 측정 수치 검색 요청")
public class ListGasLogRequest extends DefaultPageRequest  {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, macAddress: Mac주소, deviceId: 디바이스 식별자")
  private String searchName = "COMPLEX";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @NotBlank(message = "현장 아이디는 필수입니다.")
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @ApiModelProperty(notes = "디바이스 넘버")
  private Long idx;

  @NotNull(message = "검색 시작일은 필수입니다.")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  @ApiModelProperty(notes = "검색 시작일. yyyyMMdd ")
  private Date startDate;

  @NotNull(message = "검색 종료일은 필수입니다.")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
  @JsonDeserialize(using = SqlDateDeserializer.class)
  @ApiModelProperty(notes = "검색 종료일. yyyyMMdd ")
  private Date endDate;

  @JsonIgnore
  @NotNull(message = "검색 시작/종료일은 필수입니다.")
  @Max(value = 2, message = "검색 범위는 3일 이내여야 합니다")
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
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    if(idx != null ){
      condition.put("idx", idx);
    }
    if(startDate != null ){
      condition.put("startDate", startDate);
    }
    if(endDate != null ){
      condition.put("endDate", endDate);
    }
    return condition;
  }
}
