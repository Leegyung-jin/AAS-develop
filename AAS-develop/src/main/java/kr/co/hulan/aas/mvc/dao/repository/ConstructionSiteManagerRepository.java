package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSite;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteKey;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteManager;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteManagerKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstructionSiteManagerRepository extends JpaRepository<ConstructionSiteManager, ConstructionSiteManagerKey> {

  List<ConstructionSiteManager> findByWpIdAndCcId(String wpId, String ccId);

  //
  boolean existsByMbId(String mbId);
  boolean existsByMbIdAndWpIdNot(String mbId, String wpId);
}
