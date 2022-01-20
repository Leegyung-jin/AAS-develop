package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceGroup;
import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceGroupKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkGeofenceGroupRepository extends
    JpaRepository<WorkGeofenceGroup, WorkGeofenceGroupKey> {

  @Query("SELECT max(WGG.wpSeq) FROM WorkGeofenceGroup WGG WHERE WGG.wpId = :wpId ")
  Integer getMaxWpSeqByWpId(@Param("wpId") String wpId );
}
