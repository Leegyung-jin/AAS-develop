package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.ImageAnalyticInfoDto;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageAnalyticInfoDao {

  List<ImageAnalyticInfoDto> findList(Map<String,Object> condition);
}
