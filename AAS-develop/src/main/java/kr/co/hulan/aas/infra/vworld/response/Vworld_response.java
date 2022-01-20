package kr.co.hulan.aas.infra.vworld.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by hulan on 2020-04-27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vworld_response {
  private Vworld_service service;
  private String status;
  private Vworld_input input;
  private Vworld_refined refined;
  private Vworld_result result;

  public Vworld_service getService() {
    return service;
  }

  public void setService(Vworld_service service) {
    this.service = service;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Vworld_input getInput() {
    return input;
  }

  public void setInput(Vworld_input input) {
    this.input = input;
  }

  public Vworld_refined getRefined() {
    return refined;
  }

  public void setRefined(Vworld_refined refined) {
    this.refined = refined;
  }

  public Vworld_result getResult() {
    return result;
  }

  public void setResult(Vworld_result result) {
    this.result = result;
  }

  public boolean isOk(){
    return isResultOk() && containsValidData();
  }

  public boolean isResultOk(){
    return StringUtils.equalsIgnoreCase(getStatus(), "ok");
  }

  public boolean containsValidData(){
    return result != null
        && result.hasValidPoint()
    ;
  }
}
