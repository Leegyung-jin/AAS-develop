package kr.co.hulan.aas.mvc.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingFloorCompositeKey implements Serializable  {

    private Long buildingNo;
    private Integer floor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingFloorCompositeKey that = (BuildingFloorCompositeKey) o;
        return Objects.equals(buildingNo, that.buildingNo) &&
                Objects.equals(floor, that.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingNo, floor);
    }
}
