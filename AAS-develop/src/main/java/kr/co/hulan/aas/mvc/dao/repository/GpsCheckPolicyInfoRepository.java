package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.GpsCheckPolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GpsCheckPolicyInfoRepository extends JpaRepository<GpsCheckPolicyInfo, Integer> {

    @Modifying
    @Query("delete from GpsCheckPolicyInfo GCPI where GCPI.wpId =:wpId")
    void deleteAllByWpId(String wpId);

    GpsCheckPolicyInfo findByWpId(String wpId);

    long countByWpId(String wpId);
    long countByWpIdAndIdxNot(String wpId, int idx);
}
