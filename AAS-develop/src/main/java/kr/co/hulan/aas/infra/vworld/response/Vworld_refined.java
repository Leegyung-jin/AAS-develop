package kr.co.hulan.aas.infra.vworld.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hulan on 2020-04-27.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vworld_refined {
  private String text;
  private Vworld_structure structure;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Vworld_structure getStructure() {
    return structure;
  }

  public void setStructure(Vworld_structure structure) {
    this.structure = structure;
  }
}
