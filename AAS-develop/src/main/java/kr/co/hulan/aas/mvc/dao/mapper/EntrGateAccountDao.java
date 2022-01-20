package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateAccountListVo;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateAccountDetailVo;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrGateAccountDao {
  List<EntrGateAccountListVo> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  EntrGateAccountDetailVo findInfo(String accountId);


}
