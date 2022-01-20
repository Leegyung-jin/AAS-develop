package kr.co.hulan.aas.mvc.dao.repository;

import java.util.Date;
import kr.co.hulan.aas.mvc.model.domain.nvr.NvrEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvrEventLogRepository extends JpaRepository<NvrEventLog, String> {

  NvrEventLog findFirstByElogId(String elogId);
}
