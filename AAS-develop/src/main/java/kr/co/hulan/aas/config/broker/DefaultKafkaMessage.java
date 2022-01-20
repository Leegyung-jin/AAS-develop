package kr.co.hulan.aas.config.broker;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DefaultKafkaMessage<T> {

  @ApiModelProperty(notes = "이벤트 유형")
  private Integer eventType;
  @ApiModelProperty(notes = "이벤트 발생 시간")
  private Long eventTime = System.currentTimeMillis();
  @ApiModelProperty(notes = "이벤트 관련 정보")
  private T context;

  public boolean isValid(){
    return eventTime != null && eventType != null;
  }

}
