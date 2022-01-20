package kr.co.hulan.aas.infra.vworld.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hulan on 2020-04-27.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vworld {
  private Vworld_response response;

  public Vworld_response getResponse() {
    return response;
  }

  public void setResponse(Vworld_response response) {
    this.response = response;
  }
}
