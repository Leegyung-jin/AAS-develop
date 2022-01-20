package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.hicc.vo.ImosNoticeVo;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeDto;
import org.springframework.stereotype.Repository;

@Repository
public interface ImosNoticeDao {

  List<ImosNoticeDto> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  ImosNoticeDto findInfo(long imntNo);

  ImosNoticeVo findDetailInfo(long imntNo);

}
