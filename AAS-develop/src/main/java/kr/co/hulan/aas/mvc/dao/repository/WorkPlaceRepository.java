package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkPlaceRepository extends JpaRepository<WorkPlace, String> {

    List<WorkPlace> findAllByCcIdOrderByWpNameAsc(String cc_id);
    Optional<WorkPlace> findByManMbId(String manMbId);

    long countByManMbId(String manMbId);
    long countByManMbIdAndWpIdNot(String manMbId, String wpId);
}
