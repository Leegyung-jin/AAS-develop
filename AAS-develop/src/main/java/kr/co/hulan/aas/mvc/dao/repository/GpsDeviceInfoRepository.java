package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.GpsDeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GpsDeviceInfoRepository extends JpaRepository<GpsDeviceInfo, Integer> {

  @Modifying
  @Query("delete from GpsDeviceInfo GDI where GDI.wpId =:wpId")
  void deleteAllByWpId(String wpId);

}
