package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.AlarmCheckPolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AlarmCheckPolicyInfoRepository extends JpaRepository<AlarmCheckPolicyInfo, Long> {

  @Modifying
  @Query("delete from AlarmCheckPolicyInfo ACPI where ACPI.wpId =:wpId")
  void deleteAllByWpId(String wpId);
}
