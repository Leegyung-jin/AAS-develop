package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.OfficeWorkplaceGrp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeWorkplaceGrpRepository extends JpaRepository<OfficeWorkplaceGrp, Long> {

}
