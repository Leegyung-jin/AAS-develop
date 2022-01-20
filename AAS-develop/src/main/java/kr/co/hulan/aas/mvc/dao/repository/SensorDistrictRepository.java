package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.SensorDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorDistrictRepository extends JpaRepository<SensorDistrict, Integer> {

    List<SensorDistrict> findAllByWpIdOrderBySdNameDesc(String wpId);
    long countByWpIdAndSdName(String wpId, String siCode);

    @Modifying
    @Query("delete from SensorDistrict SD where SD.wpId =:wpId")
    void deleteAllByWpId(String wpId);
}
