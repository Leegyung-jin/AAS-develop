package kr.co.hulan.aas.mvc.dao.mapper;


import kr.co.hulan.aas.mvc.model.domain.WorkPlaceMonitorConfig;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceMonitorConfigDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceMonitorConfigDao {

  WorkPlaceMonitorConfigDto findById(@Param(value = "wpId") String wpId);

  int create(WorkPlaceMonitorConfigDto dto);
}
