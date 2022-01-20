package kr.co.hulan.aas.mvc.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="GasSafeRangeDto", description="GAS 안전 수치 정보")
@AllArgsConstructor
@NoArgsConstructor
public class GasSafeRangeDto {

  @ApiModelProperty(notes = "측정 카테고리. 1.온도 2.산소 3.황화수소 4.일산화탄소 5.메탄(탄산)")
  private Integer category;
  @ApiModelProperty(notes = "측정 카테고리명")
  private String categoryName;
  @ApiModelProperty(notes = "정상 최소값")
  private BigDecimal min;
  @ApiModelProperty(notes = "정상 최대값")
  private BigDecimal max;
  @ApiModelProperty(notes = "측정 최소값")
  private BigDecimal minMeasure;
  @ApiModelProperty(notes = "측정 최대값")
  private BigDecimal maxMeasure;
  @ApiModelProperty(notes = "단위")
  private String unit;
}
