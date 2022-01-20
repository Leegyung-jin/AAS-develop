package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.workplace.vo.ConstructionSiteManagerVo;
import kr.co.hulan.aas.mvc.api.workplace.vo.ConstrunctionSiteVo;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteKey;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteManager;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionSiteDao {

  List<ConstrunctionSiteVo> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  ConstrunctionSiteVo findInfo(ConstructionSiteKey key);

  List<ConstructionSiteManagerVo> findConstructionSiteMangerListByKey(ConstructionSiteKey key);
}
