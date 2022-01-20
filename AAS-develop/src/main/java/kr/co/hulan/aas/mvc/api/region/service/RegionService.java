package kr.co.hulan.aas.mvc.api.region.service;

import java.util.List;
import kr.co.hulan.aas.mvc.api.region.vo.RegionSidoVo;
import kr.co.hulan.aas.mvc.dao.mapper.RegionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

  @Autowired
  private RegionDao regionDao;

  public List<RegionSidoVo> findSidoList(){
    return regionDao.findSidoList();
  }
}
