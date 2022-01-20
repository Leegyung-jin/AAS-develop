package kr.co.hulan.aas.mvc.api.tracker.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.utils.SqlTimeDeserializer;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateTrackerRequest", description="트랙커 생성 요청")
public class CreateTrackerRequest {

  @NotEmpty
  @ApiModelProperty(notes = "트랙커 아이디", required = true)
  private String trackerId;

  @NotEmpty
  @ApiModelProperty(notes = "트랙커 디바이스 모델명", required = true)
  private String deviceModel;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

}
