package kr.co.hulan.aas.mvc.dao.repository;

import java.util.List;
import kr.co.hulan.aas.mvc.model.domain.SensorPolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SensorPolicyInfoRepository extends JpaRepository<SensorPolicyInfo, Integer> {

  @Modifying
  @Query("delete from SensorPolicyInfo SPI where SPI.wpId =:wpId")
  void deleteAllByWpId(String wpId);

  List<SensorPolicyInfo> findByWpIdIsNullAndSiTypeNot(String siType);
}
