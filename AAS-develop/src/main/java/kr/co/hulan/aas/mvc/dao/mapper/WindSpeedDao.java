package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindGaugeStatusVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedFigureVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedRangeVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedRecentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WindSpeedDao {

  public List<WindGaugeStatusVo> findWindGaugeStatusList(@Param(value="wpId") String wpId );

  public List<WindSpeedRecentVo> findWindGaugeList(@Param(value="wpId") String wpId );

  public WindGaugeStatusVo findWindGaugeStatus(@Param(value="idx") int idx );

  public List<WindSpeedFigureVo> findWindSpeedHistoryList(Map<String,Object> condition);

  public List<WindSpeedRangeVo> findWindSpeedRangeListByWpId(@Param(value="wpId") String wpId);


}
