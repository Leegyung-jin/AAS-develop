package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosTiltDeviceStateVo;
import kr.co.hulan.aas.mvc.model.dto.TiltLogDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TiltLogDao {

  public List<ImosTiltDeviceStateVo> findTiltDeviceStateList(Map<String,Object> condition);
  public Integer countTiltDeviceStateList(Map<String,Object> condition);

  ImosTiltDeviceStateVo findTiltDeviceState(@Param(value = "wpId") String wpId, @Param(value = "idx") int idx);

  List<TiltLogDto> findTiltLogHistoryList(@Param(value = "macAddress") String macAddress, @Param(value = "historyCount") int historyCount);
}
