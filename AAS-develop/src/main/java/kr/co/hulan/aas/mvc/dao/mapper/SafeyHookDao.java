package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookCurrentStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosSafetyHookCoopStateVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosSafetyHookWorkerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SafeyHookDao {

  public ImosSafetyHookCurrentStateResponse findCurrentState(String wpId);

  List<ImosSafetyHookCoopStateVo> findCurrentCoopStateList(
      @Param(value = "wpId") String wpId,
      @Param(value = "eventType") int type,
      @Param(value = "state") int state
  );

  List<ImosSafetyHookWorkerVo> findCurrentWorkers(
      @Param(value = "wpId") String wpId,
      @Param(value = "eventType") int type,
      @Param(value = "state") int state,
      @Param(value = "coopMbId") String coopMbId
  );

  Long countTodaySafeyHookByWpId(@Param(value = "wpId") String wpId);

}
