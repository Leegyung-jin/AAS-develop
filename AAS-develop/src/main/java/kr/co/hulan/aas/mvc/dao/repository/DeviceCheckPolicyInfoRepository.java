package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.DeviceCheckPolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DeviceCheckPolicyInfoRepository extends JpaRepository<DeviceCheckPolicyInfo, Long> {

  @Modifying
  @Query("delete from DeviceCheckPolicyInfo DCPI where DCPI.wpId =:wpId")
  void deleteAllByWpId(String wpId);
}
