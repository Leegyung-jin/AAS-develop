package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.GpsLocation;
import kr.co.hulan.aas.mvc.model.domain.GpsLocationCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GpsLocationRepository extends JpaRepository<GpsLocation, GpsLocationCompositeKey> {

  @Modifying
  @Query("delete from GpsLocation GL where GL.wpId =:wpId")
  void deleteAllByWpId(String wpId);
}
