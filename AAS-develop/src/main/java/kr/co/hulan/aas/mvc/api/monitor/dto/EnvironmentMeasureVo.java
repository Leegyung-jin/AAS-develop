package kr.co.hulan.aas.mvc.api.monitor.dto;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import kr.co.hulan.aas.common.code.MeasureEnvironmentType;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.mvc.model.dto.GasSafeRangeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EnvironmentMeasureVo", description="유해물질 측정 정보")
public class EnvironmentMeasureVo {

  private String name;
  private String unit;
  private BigDecimal value;
  private Integer level;
  private String iconUrl;
  private BigDecimal min;
  private BigDecimal max;


  public static EnvironmentMeasureVo build(
      MeasureEnvironmentType type, BigDecimal value, Integer level, GasSafeRangeDto safeRange
  ){
    EnvironmentMeasureVo vo = new EnvironmentMeasureVo();
    vo.setName(type.getName());
    vo.setValue(value);
    vo.setLevel(level);
    if( safeRange != null ){
      vo.setUnit(safeRange.getUnit());
      vo.setMin(safeRange.getMin());
      vo.setMax(safeRange.getMax());
    }
    else {
      vo.setUnit(type.getUnit());
    }
    vo.setIconUrl(Storage.LOCAL_STORAGE.getDownloadUrlBase()+type.getIconFilePath());
    return vo;
  }
}
