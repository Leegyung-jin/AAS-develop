package kr.co.hulan.aas.infra.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KmaWetherResponse<T> {

  private KmaWetherHeaderResponse header;
  private KmaWetherBodyResponse<T> body;

  public KmaWetherHeaderResponse getHeader() {
    return header;
  }

  public void setHeader(KmaWetherHeaderResponse header) {
    this.header = header;
  }

  public KmaWetherBodyResponse<T> getBody() {
    return body;
  }

  public void setBody(KmaWetherBodyResponse<T> body) {
    this.body = body;
  }

}
