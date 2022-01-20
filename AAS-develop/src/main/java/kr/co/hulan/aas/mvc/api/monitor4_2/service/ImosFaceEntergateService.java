package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnterGateType;
import kr.co.hulan.aas.common.code.WorkInOutMethod;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerHistoryPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.EntergateMemberStatResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateStatInfo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateSystemInfo;
import kr.co.hulan.aas.mvc.dao.mapper.EntrGateWorkplaceDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImosFaceEntergateService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  EntrGateWorkplaceDao entrGateWorkplaceDao;

  public EntergateMemberStatResponse findGateSystemStat(String wpId){
    EnterGateType gateType = EnterGateType.FACE_RECOGNITION;

    EntrGateWorkplaceVo workplaceVo = entrGateWorkplaceDao.findInfo(wpId);
    if( workplaceVo == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입시스템 정보를 찾을 수 없습니다.");
    }
    else if( EnterGateType.get(workplaceVo.getGateType()) != gateType ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "안면인식 출입시스템 정보를 찾을 수 없습니다.");
    }

    List<Integer> workInMethods = new ArrayList<>();
    workInMethods.add(WorkInOutMethod.ENTER_GATE.getCode());

    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId",wpId);
    condition.put("workInMethods", Collections.EMPTY_LIST);
    condition.put("gateType", gateType.getCode());
    EnterGateStatInfo statVo =  entrGateWorkplaceDao.findCurrentGateStat(condition);
    if( statVo == null ){
      statVo = new EnterGateStatInfo();
      statVo.setAttendanceCount(0);
      statVo.setNotUseAppCount(0);
      statVo.setResidualCount(0);
    }
    return EntergateMemberStatResponse.builder()
        .system(modelMapper.map(workplaceVo, EnterGateSystemInfo.class))
        .stat(statVo)
        .build();
  }

  public List<AttendantVo> findGateSystemEntranceUserList(String wpId, EntergateWorkerListRequest request){
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);

    List<Integer> workInMethods = new ArrayList<>();
    workInMethods.add(WorkInOutMethod.ENTER_GATE.getCode());
    condition.put("workInMethods", workInMethods);

    condition.put("gateType", EnterGateType.FACE_RECOGNITION.getCode());
    return entrGateWorkplaceDao.findGateSystemEntranceUserList(condition);
  }

  public DefaultPageResult<AttendantVo> findGateEntranceHistoryPagingList(String wpId, EntergateWorkerHistoryPagingListRequest request){
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);

    List<Integer> workInMethods = new ArrayList<>();
    workInMethods.add(WorkInOutMethod.ENTER_GATE.getCode());
    condition.put("workInMethods", workInMethods);

    condition.put("gateType", EnterGateType.FACE_RECOGNITION.getCode());

    return DefaultPageResult.<AttendantVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .list(findGateEntranceHistoryList(condition))
        .totalCount(entrGateWorkplaceDao.countGateEntranceHistoryList(condition))
        .build();
  }

  public List<AttendantVo> findGateEntranceHistoryList(Map<String,Object> condition){

    List<Integer> workInMethods = new ArrayList<>();
    workInMethods.add(WorkInOutMethod.ENTER_GATE.getCode());
    condition.put("workInMethods", workInMethods);

    condition.put("gateType", EnterGateType.FACE_RECOGNITION.getCode());

    return entrGateWorkplaceDao.findGateEntranceHistoryList(condition);
  }
}
