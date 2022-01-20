package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCctvDto;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceCctvDao {
  List<WorkPlaceCctvDto> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  WorkPlaceCctvDto findInfo(long cctvNo);

  List<HiccWorkplaceSummaryVo> findWorkplaceSupportCctv(Map<String,Object> condition);
}
