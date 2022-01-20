package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.BuildingFloor;
import kr.co.hulan.aas.mvc.model.domain.BuildingFloorCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingFloorRepository extends JpaRepository<BuildingFloor, BuildingFloorCompositeKey>  {

    List<BuildingFloor> findAllByBuildingNo(long buildingNo);

    @Modifying
    @Query("delete from BuildingFloor BDF where BDF.buildingNo =:buildingNo and BDF.floorType = 1 and ( BDF.floor > :maxFloor or BDF.floor < :minFloor ) ")
    void deleteInvalidFloor(long buildingNo, int maxFloor, int minFloor);

    BuildingFloor findTopByBuildingNoOrderByFloorDesc(long buildingNo);


}
