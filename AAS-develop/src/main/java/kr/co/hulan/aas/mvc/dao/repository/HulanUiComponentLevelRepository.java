package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.HulanUiComponentLevel;
import kr.co.hulan.aas.mvc.model.domain.HulanUiComponentLevelKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HulanUiComponentLevelRepository extends
    JpaRepository<HulanUiComponentLevel, HulanUiComponentLevelKey> {

  @Modifying
  @Query("delete from HulanUiComponentLevel HUCL where HUCL.hcmptId =:hcmptId")
  int deleteByHcmptId(String hcmptId);
}
