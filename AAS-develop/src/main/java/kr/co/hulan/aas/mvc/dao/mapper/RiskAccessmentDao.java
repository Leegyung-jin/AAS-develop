package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentInspectDetailVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentInspectVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.risk.HiccRiskAccessmentStateVo;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskAccessmentDao {

  HiccRiskAccessmentStateVo findState(Map<String,Object> condition);

  List<HiccRiskAccessmentInspectVo> findInspectList(Map<String,Object> condition);
  long countInspectList(Map<String,Object> condition);

  HiccRiskAccessmentInspectDetailVo findInspectInfo(long raiNo);

  List<String> findSectionList();
}
