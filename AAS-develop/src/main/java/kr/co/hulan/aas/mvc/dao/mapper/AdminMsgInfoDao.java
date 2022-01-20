package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.dto.AdminMsgInfoDto;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMsgInfoDao {

  List<AdminMsgInfoDto> findListByCondition(Map<String,Object> condition);
  Long findListCountByCondition(Map<String,Object> condition);

  AdminMsgInfoDto findById(long buildingNo);

  List<AdminMsgInfoDto> findRecentList(Map<String,Object> condition);
}
