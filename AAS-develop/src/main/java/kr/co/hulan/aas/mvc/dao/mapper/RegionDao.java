package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import kr.co.hulan.aas.mvc.api.region.vo.RegionSidoVo;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionDao {

  List<RegionSidoVo> findSidoList();
}
