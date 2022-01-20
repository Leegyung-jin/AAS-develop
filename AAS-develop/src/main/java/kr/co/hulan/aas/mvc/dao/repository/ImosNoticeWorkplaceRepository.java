package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.imosNotice.ImosNoticeWorkplace;
import kr.co.hulan.aas.mvc.model.domain.imosNotice.ImosNoticeWorkplaceKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImosNoticeWorkplaceRepository extends JpaRepository<ImosNoticeWorkplace, ImosNoticeWorkplaceKey> {

  List<ImosNoticeWorkplace> findByImntNo(long imntNo);
}
