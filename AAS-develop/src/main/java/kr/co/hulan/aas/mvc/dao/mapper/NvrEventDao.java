package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.NvrEventDetailVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.NvrEventSummaryVo;
import kr.co.hulan.aas.mvc.model.dto.NvrEventDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NvrEventDao {

  List<NvrEventSummaryVo> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  NvrEventDetailVo findById(@Param(value = "eventNo") long eventNo);
}
