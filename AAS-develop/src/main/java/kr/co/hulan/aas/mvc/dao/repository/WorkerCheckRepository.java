package kr.co.hulan.aas.mvc.dao.repository;


import kr.co.hulan.aas.mvc.model.domain.WorkerCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerCheckRepository extends JpaRepository<WorkerCheck, Integer> {

    Optional<WorkerCheck> findByWpIdAndWorkerMbIdAndWcType(String wpId, String workerMbId, int wcType);
}
