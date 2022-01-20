package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.PushLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushLogRepository extends JpaRepository<PushLog, Integer> {
}
