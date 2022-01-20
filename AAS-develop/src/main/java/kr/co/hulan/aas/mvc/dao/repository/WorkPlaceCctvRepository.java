package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkPlaceCctv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceCctvRepository extends JpaRepository<WorkPlaceCctv, Long> {

  long countByWpId(String wpId);

  WorkPlaceCctv findByGid(String gid);
}
