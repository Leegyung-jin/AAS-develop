package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.EntrGateWorkplace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrGateWorkplaceRepository extends JpaRepository<EntrGateWorkplace, String> {

  boolean existsById(String wpId);

  EntrGateWorkplace findByAccountIdAndMappingCd(String accountId, String mappingCd);
  boolean existsByAccountIdAndMappingCdAndWpIdIsNot(String accountId, String mappingCd, String wpId);

  List<EntrGateWorkplace> findByAccountId(String accountId);

  void deleteAllByAccountId(String accountId);

}
