package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkPlaceWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkPlaceWorkerRepository extends JpaRepository<WorkPlaceWorker, String> {

    @Modifying
    @Query("delete from WorkPlaceWorker WPW where WPW.wpId =:wpId")
    void deleteAllByWpId(String wpId);

    @Modifying
    @Query("update WorkPlaceWorker set workSectionB = null where wpId =:wpId and coopMbId =:coopMbId")
    void updateResetWorkerSectionsByCoopMbId(String wpId, String coopMbId);


    long countByWpIdAndCoopMbIdAndWorkerMbId(String wpId, String coopMbId, String workerMbId);
    long countByWpIdAndWorkerMbId(String wpId, String workerMbId);

}
