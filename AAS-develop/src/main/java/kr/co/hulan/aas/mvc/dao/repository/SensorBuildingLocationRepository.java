package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.SensorBuildingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorBuildingLocationRepository extends JpaRepository<SensorBuildingLocation, Integer> {

  @Modifying
  @Query("delete from SensorBuildingLocation SBL where SBL.buildingNo =:buildingNo and ( SBL.floor > :maxFloor or SBL.floor < :minFloor ) ")
  void deleteInvalidFloorSensor(long buildingNo, int maxFloor, int minFloor);

  @Modifying
  @Query("delete from SensorBuildingLocation SBL where SBL.buildingNo =:buildingNo and SBL.floor in :floors ")
  void deleteInvalidFloorSensor(long buildingNo, List<Integer> floors);

  long countByBuildingNoAndFloor(long buildingNo, int floor);

}
