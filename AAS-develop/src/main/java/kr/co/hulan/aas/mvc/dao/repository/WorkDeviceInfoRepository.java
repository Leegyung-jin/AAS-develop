package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkDeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkDeviceInfoRepository extends JpaRepository<WorkDeviceInfo, Integer> {
    Optional<WorkDeviceInfo> findWorkDeviceInfoByWpIdAndDeviceId(String wpId, String deviceId);

    @Modifying
    @Query("delete from WorkDeviceInfo WDI where WDI.wpId =:wpId")
    void deleteAllByWpId(String wpId);

    long countByWpIdAndDeviceId(String wpId, String deviceId);
    long countByWpIdAndDeviceIdAndIdxNot(String wpId, String deviceId, int idx);

    long countByWpIdAndDeviceType(String wpId, int deviceType);
}
