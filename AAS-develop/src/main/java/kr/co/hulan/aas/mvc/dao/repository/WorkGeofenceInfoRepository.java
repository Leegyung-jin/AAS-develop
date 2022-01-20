package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceInfo;
import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceInfoCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkGeofenceInfoRepository extends JpaRepository<WorkGeofenceInfo, WorkGeofenceInfoCompositeKey> {

  @Modifying
  @Query("delete from WorkGeofenceInfo WGI where WGI.wpId =:wpId")
  void deleteAllByWpId(String wpId);

  Long deleteAllByWpIdAndWpSeq(String wpId, int wpSeq);
}
