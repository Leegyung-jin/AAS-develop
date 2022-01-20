package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkEquipmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkEquipmentInfoRepository extends JpaRepository<WorkEquipmentInfo, Integer> {

    Optional<WorkEquipmentInfo> findWorkEquipmentInfoByWpIdAndDeviceId(String wpId, String deviceId);

    @Modifying
    @Query("delete from WorkEquipmentInfo WEI where WEI.wpId =:wpId")
    void deleteAllByWpId(String wpId);

    long countByWpIdAndMbId(String wpId, String mbId);
    long countByWpIdAndMbIdAndIdxNot(String wpId, String mbId, int idx);

    long countByWpIdAndDeviceId(String wpId, String deviceId);
    long countByWpIdAndDeviceIdAndIdxNot(String wpId, String deviceId, int idx);

    // long countByEquipmentNo(String equipmentNo);
    // long countByEquipmentNoAndIdxNot(String equipmentNo, int idx);
}
