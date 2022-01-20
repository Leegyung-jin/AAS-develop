package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.HiccMainBtn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiccMainBtnRepository extends JpaRepository<HiccMainBtn, Long> {

  List<HiccMainBtn> findByHiccNo(long hiccNo);
  void deleteAllByHiccNo(long hiccNo);

}
