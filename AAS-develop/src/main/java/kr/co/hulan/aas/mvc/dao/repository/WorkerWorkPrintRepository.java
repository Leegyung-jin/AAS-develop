package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkerWorkPrint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface WorkerWorkPrintRepository extends JpaRepository<WorkerWorkPrint, Integer> {

    Optional<WorkerWorkPrint> findByWpIdAndCoopMbIdAndWwpDate(String wpId, String coopMbId, Date wwpDate);
}
