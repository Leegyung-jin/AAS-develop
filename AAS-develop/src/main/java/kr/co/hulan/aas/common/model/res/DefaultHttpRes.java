package kr.co.hulan.aas.common.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.hulan.aas.common.code.BaseCode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
public class DefaultHttpRes<T> {

  @JsonProperty("return_code")
  private Integer returnCode;

  @JsonProperty("return_message")
  private String returnMessage;

  @JsonInclude(Include.NON_NULL)
  private T context;

  private DefaultHttpRes() {
  }

  public DefaultHttpRes(int returnCode, String returnMessage) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
  }

  public DefaultHttpRes(int returnCode, String returnMessage, T context) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.context = context;
  }

  public DefaultHttpRes(BaseCode baseCode) {
    this(baseCode.code(), baseCode.getLocalizedMessage());
  }

  public DefaultHttpRes(BaseCode baseCode, T context) {
    this(baseCode.code(), baseCode.getLocalizedMessage());
    this.context = context;
  }
}
