package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccSidoWeatherVo;
import kr.co.hulan.aas.mvc.model.dto.AirEnvironmentDto;
import org.springframework.stereotype.Repository;

@Repository
public interface AirEnvironmentDao {

  AirEnvironmentDto findInfoByWpId(String wpId);

  void updateSidoAirEnvironment();

  List<HiccSidoWeatherVo> findSidoWeatherList();
}
