package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.GasLogDto;
import kr.co.hulan.aas.mvc.model.dto.GasSafeRangeDto;
import org.springframework.stereotype.Repository;

@Repository
public interface GasLogDao {
  List<GasLogDto> findRecentGasLog(String wpId);
  GasLogDto findRecentGasLogByDevice(int idx);

  List<GasSafeRangeDto> findGasSafeRangeDtoList(String wpId);

  List<GasLogDto> findListByCondition(Map<String,Object> condition);
  long findListCountByCondition(Map<String,Object> condition);
}
