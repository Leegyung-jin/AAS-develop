package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.BookmarkGps;
import kr.co.hulan.aas.mvc.model.domain.BookmarkGpsKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkGpsRepository extends JpaRepository<BookmarkGps, BookmarkGpsKey>  {

  long countByMbIdAndWpId(String mbId, String wpId);
}
