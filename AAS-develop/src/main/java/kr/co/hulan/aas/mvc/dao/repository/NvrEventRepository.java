package kr.co.hulan.aas.mvc.dao.repository;

import java.util.Date;
import kr.co.hulan.aas.mvc.model.domain.nvr.NvrEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvrEventRepository extends JpaRepository<NvrEvent, Long>  {

  NvrEvent findFirstByElogIdAndStartTmAfterOrderByStartTmDesc(String elogId, Date startTm);
}
