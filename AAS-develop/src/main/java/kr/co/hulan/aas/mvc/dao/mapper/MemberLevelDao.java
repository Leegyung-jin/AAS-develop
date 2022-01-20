package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.MemberLevelDto;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLevelDao {

  List<MemberLevelDto> findListByCondition(Map<String,Object> condition);
}
