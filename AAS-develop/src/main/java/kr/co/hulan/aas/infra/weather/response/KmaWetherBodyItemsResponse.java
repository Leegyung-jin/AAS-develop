package kr.co.hulan.aas.infra.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KmaWetherBodyItemsResponse<T> {

  private List<T> item;

  public List<T> getItem() {
    return item;
  }

  public void setItem(List<T> item) {
    this.item = item;
  }
}
