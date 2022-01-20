package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OfficeWorkplaceGroupVo;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeWorkplaceGroupDao {
  List<OfficeWorkplaceGroupVo> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  OfficeWorkplaceGroupVo findInfo(long wpGrpNo);
}
