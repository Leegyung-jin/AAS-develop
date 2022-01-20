package kr.co.hulan.aas.infra.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KmaResponse<T> {

  private KmaWetherResponse<T> response;

  public boolean isOk(){
    return isResultOk() && containItemDatas();
  }

  public boolean isResultOk(){
    return response != null
        && response.getHeader() != null
        && response.getHeader().isResultOk()
        ;
  }

  public String getResult(){
    if( response != null && response.getHeader() != null ){
      return response.getHeader().getResultCode();
    }
    else {
      return "InvalidResponse";
    }
  }

  public boolean containItemDatas(){
    return response != null
        && response.getBody() != null
        && response.getBody().containsItemData()
        ;
  }

}
