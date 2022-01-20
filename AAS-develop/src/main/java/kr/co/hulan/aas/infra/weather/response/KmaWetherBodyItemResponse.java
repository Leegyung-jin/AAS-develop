package kr.co.hulan.aas.infra.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KmaWetherBodyItemResponse {

  private String baseDate;
  private String baseTime;
  private String category;
  private String nx;
  private String ny;
  private String obsrValue;

  public String getBaseDate() {
    return baseDate;
  }

  public void setBaseDate(String baseDate) {
    this.baseDate = baseDate;
  }

  public String getBaseTime() {
    return baseTime;
  }

  public void setBaseTime(String baseTime) {
    this.baseTime = baseTime;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getNx() {
    return nx;
  }

  public void setNx(String nx) {
    this.nx = nx;
  }

  public String getNy() {
    return ny;
  }

  public void setNy(String ny) {
    this.ny = ny;
  }

  public String getObsrValue() {
    return obsrValue;
  }

  public void setObsrValue(String obsrValue) {
    this.obsrValue = obsrValue;
  }

  public LocalDateTime getBaseDateTime(){
    return LocalDateTime.parse(baseDate+baseTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
  }

  public String getValue(){
    return getObsrValue();
  }
}
