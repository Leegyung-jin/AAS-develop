package kr.co.hulan.aas.mvc.api.msg.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.AdminMsgInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListAdminMsgInfoResponse", description="현장 관리자 알림 메시지 리스트 응답")
public class ListAdminMsgInfoResponse {
  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "현장 관리자 알림 메시지 리스트")
  private List<AdminMsgInfoDto> list;
}
