package kr.co.hulan.aas.mvc.api.jobMgr.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateRecruitApplyMultiRequest", description="구직 정보 멀티 생성 요청")
public class CreateRecruitApplyMultiRequest {

  @Size(min=1)
  @NotNull
  @ApiModelProperty(notes = "근로자 구직 정보 리스트", required = true)
  private List<CreateRecruitApplyRequest> workerList;

}
