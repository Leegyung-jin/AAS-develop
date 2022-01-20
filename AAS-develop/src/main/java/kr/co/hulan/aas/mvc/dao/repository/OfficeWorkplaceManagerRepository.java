package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.OfficeWorkplaceManager;
import kr.co.hulan.aas.mvc.model.domain.OfficeWorkplaceManagerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeWorkplaceManagerRepository extends JpaRepository<OfficeWorkplaceManager, OfficeWorkplaceManagerKey> {

  List<OfficeWorkplaceManager> findByWpGrpNo(long wpGrpNo);
}
