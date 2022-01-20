package kr.co.hulan.aas.infra.airkorea.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="DataGoKrCommonResponseMessage", description="공공 API 응답 전문")
public class DataGoKrCommonResponseMessage<T> {

  @ApiModelProperty(notes = "응답")
  private DataGoKrCommonResponse<T> response;

  public boolean isSuccess(){
    return response != null
        && response.getHeader() != null
        && StringUtils.equals(response.getHeader().getResultCode(), "00")
        && getResponseBody() != null
        ;
  }

  public T getResponseBody(){
    return response != null ? response.getBody() : null;
  }
}
