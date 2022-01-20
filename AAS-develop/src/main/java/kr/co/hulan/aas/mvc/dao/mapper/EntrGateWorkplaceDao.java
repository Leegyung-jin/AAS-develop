package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CurrentWorkStatusSummary;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateStatInfo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosAttendantManagerVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosQrGateCoopStateVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosQrGateAttendantStateVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosWorkingWorkerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrGateWorkplaceDao {
  List<EntrGateWorkplaceVo> findListByCondition(Map<String,Object> condition);
  Long countListByCondition(Map<String,Object> condition);

  EntrGateWorkplaceVo findInfo(String wpId);

  /*
  EnterGateStatInfo findCurrentGateStat(
      @Param(value = "wpId") String wpId,
      @Param(value = "workInMethods") List<Integer> workInMethods,
      @Param(value = "gateType") Integer gateType
  );
   */
  EnterGateStatInfo findCurrentGateStat(Map<String,Object> condition);

  List<AttendantVo> findGateSystemEntranceUserList(Map<String,Object> condition);

  List<AttendantVo> findGateEntranceHistoryList(Map<String,Object> condition);
  Long countGateEntranceHistoryList(Map<String,Object> condition);

  ImosQrGateAttendantStateVo findQrGateState(String wpId);
  CurrentWorkStatusSummary findQrReaderWorkStatus(String wpId);

  List<ImosQrGateCoopStateVo> findQrGateCoopStateList(
      @Param(value = "wpId") String wpId,
      @Param(value = "attendantType") Integer attendantType
  );

  List<AttendantVo> findQrGateCoopWorkerList(
      @Param(value = "wpId") String wpId,
      @Param(value = "attendantType") Integer attendantType,
      @Param(value = "coopMbId") String coopMbId
  );
  List<ImosAttendantManagerVo> findQrGateManagerList(
      @Param(value = "wpId") String wpId
  );

  List<ImosQrGateCoopStateVo> findQrGateCoopListByWorkingStatus(
      @Param(value = "wpId") String wpId,
      @Param(value = "currentWorking") Integer currentWorking
  );

  List<ImosWorkingWorkerVo> findQrGateCoopWorkerListByWorkingStatus(
      @Param(value = "wpId") String wpId,
      @Param(value = "currentWorking") Integer currentWorking,
      @Param(value = "coopMbId") String coopMbId
  );

}
