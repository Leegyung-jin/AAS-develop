package kr.co.hulan.aas.mvc.api.workplace.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceWorkerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateMultiWorkplaceWorkerResultResponse", description="근로자 현장 편입 멀티 생성 결과")
public class CreateMultiWorkplaceWorkerResultResponse {

  @ApiModelProperty(notes = "고위험/주요근로자 리스트")
  private List<CreateResult> result = new ArrayList<CreateResult>();

  @JsonIgnore
  public void addResult(int returnCode, String returnMessage, String mbId, WorkPlaceWorkerDto workPlaceWorker){
    result.add(new CreateResult(returnCode, returnMessage, mbId, workPlaceWorker ));
  }

  @Data
  @AllArgsConstructor
  public static class CreateResult {
    private Integer returnCode;
    private String returnMessage;
    private String mbId;
    private WorkPlaceWorkerDto workPlaceWorker;

  }
}
