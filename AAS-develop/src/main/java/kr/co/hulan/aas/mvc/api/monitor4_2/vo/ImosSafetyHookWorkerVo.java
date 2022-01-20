package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosSafetyHookWorkerVo", description="IMOS 안전고리 근로자 정보")
public class ImosSafetyHookWorkerVo {

  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "근로자명")
  private String mbName;
  @ApiModelProperty(notes = "근로자 공종코드")
  private String workSectionB;
  @ApiModelProperty(notes = "근로자 공종명")
  private String workSectionNameB;
  @ApiModelProperty(notes = "페어링 여부. 0: 해제, 1: 연결")
  private Integer pairingStatus;
  @ApiModelProperty(notes = "페어링 시간")
  private Date pairingDate;
  @ApiModelProperty(notes = "감시구역 진입 여부. 0: 이탈, 1: 진입")
  private Integer districtEnterStatus;
  @ApiModelProperty(notes = "감지구역 진입 시간")
  private Date districtEnterDate;
  @ApiModelProperty(notes = "안전고리 체결 여부. 0: 해제, 1: 잠김")
  private Integer lockStatus;
  @ApiModelProperty(notes = "안전고리 체결 시간")
  private Date lockDate;

}
