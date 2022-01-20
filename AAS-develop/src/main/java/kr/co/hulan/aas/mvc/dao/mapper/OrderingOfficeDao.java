package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OfficeWorkplaceGroupVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeListVo;
import kr.co.hulan.aas.mvc.api.orderoffice.vo.OrderingOfficeVo;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderingOfficeDao {

  List<OrderingOfficeListVo> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  OrderingOfficeVo findInfo(long officeNo);

}
