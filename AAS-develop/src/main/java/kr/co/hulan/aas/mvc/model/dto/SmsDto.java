package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value="SmsDto", description="SMS 전송 정보")
public class SmsDto {

  private Integer mseq;
  private String dstAddr;
  private String callback;
  private String subject;
  private String content;
}
