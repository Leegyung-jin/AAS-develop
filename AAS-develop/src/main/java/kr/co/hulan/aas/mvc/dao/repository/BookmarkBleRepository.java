package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.BookmarkBle;
import kr.co.hulan.aas.mvc.model.domain.BookmarkBleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkBleRepository extends JpaRepository<BookmarkBle, BookmarkBleKey> {

  long countByMbIdAndWpId(String mbId, String wpId);

}
