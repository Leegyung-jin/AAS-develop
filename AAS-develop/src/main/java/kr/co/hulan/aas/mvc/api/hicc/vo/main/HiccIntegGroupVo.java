package kr.co.hulan.aas.mvc.api.hicc.vo.main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccIntegGroupVo", description="권역 정보")
public class HiccIntegGroupVo {

  @ApiModelProperty(notes = "권역 번호")
  private Long hrgNo;

  @ApiModelProperty(notes = "권역명")
  private String hrgName;

  @ApiModelProperty(notes = "통합 관제 번호")
  private Long hiccNo;

  @ApiModelProperty(notes = "정렬순서")
  private Integer orderSeq;

  @ApiModelProperty(notes = "협력업체수")
  private Long totalCoopCount;

  @ApiModelProperty(notes = "권역내 현장 리스트")
  private List<HiccWorkplaceForIntegGroupVo> workplaceList;

  @ApiModelProperty(notes = "현장 수")
  public Long getTotalWorkplaceCount(){
    if(workplaceList != null ){
      return (long) workplaceList.size();
    }
    return 0L;
  }

  @ApiModelProperty(notes = "출역인원 수")
  public Long getTotalWorkerCount(){
    long sum = 0;
    if(workplaceList != null && workplaceList.size() > 0 ){
      for(HiccWorkplaceForIntegGroupVo workplace : workplaceList ){
        sum += workplace.getTotalWorkerCount();
      }
    }
    return sum;
  }

  @JsonIgnoreProperties
  public void addWorkplace(HiccWorkplaceForIntegGroupVo workplace){
    if( workplaceList == null ){
      workplaceList = new ArrayList<HiccWorkplaceForIntegGroupVo>();
    }
    workplaceList.add(workplace);
  }

}
