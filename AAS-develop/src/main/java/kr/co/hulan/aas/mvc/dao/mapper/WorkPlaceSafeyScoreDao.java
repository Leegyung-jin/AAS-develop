package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceEvaluationVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSafeyScorePerDayVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSafeyScoreVo;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceSafeyScoreDao {
  // List<HiccWorkplaceSafeyScoreVo> findTodaySafeyScoreList(Map<String, Object> condition);

  List<HiccWorkplaceSafeyScoreVo> findCurrentSafeyScoreList(Map<String, Object> condition);
  List<HiccWorkplaceSafeyScoreVo> findAverageSafeyScoreList(Map<String, Object> condition);

  HiccWorkplaceEvaluationVo findEvaluationSafeyScore(Map<String, Object> condition);

  List<HiccWorkplaceSafeyScorePerDayVo> findSafeyScoreListByWpId(Map<String, Object> condition);

  int updateSafetyScore();
}
