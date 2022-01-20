package kr.co.hulan.aas.mvc.dao.repository;


import kr.co.hulan.aas.mvc.model.domain.ConstructionSite;
import kr.co.hulan.aas.mvc.model.domain.ConstructionSiteKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstructionSiteRepository extends JpaRepository<ConstructionSite, ConstructionSiteKey> {

}
