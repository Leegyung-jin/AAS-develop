package kr.co.hulan.aas.common.model.res;

import lombok.Data;

@Data
public class ValidationResult {

  private String result;
  private String message;
  private String description;

  public ValidationResult(String result, String message, String description) {
    this.result = result;
    this.message = message;
    this.description = description;
  }
}
