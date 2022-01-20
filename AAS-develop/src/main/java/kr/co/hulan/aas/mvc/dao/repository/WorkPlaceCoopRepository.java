package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkPlaceCoop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkPlaceCoopRepository extends JpaRepository<WorkPlaceCoop, String> {

    Optional<WorkPlaceCoop> findByWpIdAndCoopMbId(String wpId, String coopMbId);

    @Modifying
    @Query("delete from WorkPlaceCoop WPC where WPC.wpId =:wpId")
    void deleteAllByWpId(String wpId);

    long countByWpIdAndCoopMbId(String wpId, String coopMbId);
}
