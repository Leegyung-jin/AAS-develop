package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnterGateType;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.code.WorkInOutMethod;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerHistoryPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CurrentWorkStatusSummary;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateStatInfo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateSystemInfo;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateAttendantListResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateCoopAttendanceResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateCoopWorkStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateCoopWorkingWorkerListResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateManagerListResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosQrGateStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosQrGateAttendantStateVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.QrGateAttendantType;
import kr.co.hulan.aas.mvc.dao.mapper.EntrGateWorkplaceDao;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImosQrEntergateService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  EntrGateWorkplaceDao entrGateWorkplaceDao;

  @Autowired
  private G5MemberDao g5MemberDao;

  public ImosQrGateStateResponse findGateSystemStat(String wpId){
    EntrGateWorkplaceVo workplaceVo = entrGateWorkplaceDao.findInfo(wpId);
    if( workplaceVo == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입시스템 정보를 찾을 수 없습니다.");
    }
    else if( EnterGateType.get(workplaceVo.getGateType()) != EnterGateType.QR_READER){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "QR Reader 출입시스템 정보를 찾을 수 없습니다.");
    }
    ImosQrGateAttendantStateVo statVo =  entrGateWorkplaceDao.findQrGateState(wpId);
    if( statVo == null ){
      statVo = new ImosQrGateAttendantStateVo();
      statVo.setQrWorkerCount(0L);
      statVo.setQrManagerCount(0L);
      statVo.setEtcWorkerCount(0L);
      statVo.setTotalWorkerCount(0L);
    }
    CurrentWorkStatusSummary summary = entrGateWorkplaceDao.findQrReaderWorkStatus(wpId);
    if( summary == null ){
      summary = CurrentWorkStatusSummary.builder()
          .currentWorkerCount(0L)
          .totalWorkerCount(0L)
          .build();
    }
    return ImosQrGateStateResponse.builder()
        .system(modelMapper.map(workplaceVo, EnterGateSystemInfo.class))
        .attendantState(statVo)
        .workStatus(summary)
        .build();
  }

  public ImosQrGateCoopAttendanceResponse findGateCoopState(String wpId, int attendantType){
    EntrGateWorkplaceVo workplaceVo = entrGateWorkplaceDao.findInfo(wpId);
    if( workplaceVo == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입시스템 정보를 찾을 수 없습니다.");
    }
    else if( EnterGateType.get(workplaceVo.getGateType()) != EnterGateType.QR_READER){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "QR Reader 출입시스템 정보를 찾을 수 없습니다.");
    }
    return ImosQrGateCoopAttendanceResponse.builder()
        .attendantType(attendantType)
        .wpId(workplaceVo.getWpId())
        .wpName(workplaceVo.getWpName())
        .coopList(entrGateWorkplaceDao.findQrGateCoopStateList(wpId, attendantType))
        .build();
  }

  public ImosQrGateAttendantListResponse findGateAttendantWorkerList(String wpId, int attendantType, String coopMbId){
    EntrGateWorkplaceVo workplaceVo = entrGateWorkplaceDao.findInfo(wpId);
    if( workplaceVo == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입시스템 정보를 찾을 수 없습니다.");
    }
    else if( EnterGateType.get(workplaceVo.getGateType()) != EnterGateType.QR_READER){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "QR Reader 출입시스템 정보를 찾을 수 없습니다.");
    }

    G5MemberDto coop = g5MemberDao.findByMbId(coopMbId);
    if( coop == null || MemberLevel.get(coop.getMbLevel()) != MemberLevel.COOPERATIVE_COMPANY ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "협력사 정보가 존재하지 않습니다.");
    }
    return ImosQrGateAttendantListResponse.builder()
        .attendantType(attendantType)
        .wpId(workplaceVo.getWpId())
        .wpName(workplaceVo.getWpName())
        .coopMbId(coop.getMbId())
        .coopMbName(coop.getName())
        .attenendantList(entrGateWorkplaceDao.findQrGateCoopWorkerList(wpId, attendantType, coopMbId))
        .build();
  }


  public ImosQrGateManagerListResponse findGateAttendantManagerList(String wpId){
    EntrGateWorkplaceVo workplaceVo = entrGateWorkplaceDao.findInfo(wpId);
    if( workplaceVo == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입시스템 정보를 찾을 수 없습니다.");
    }
    else if( EnterGateType.get(workplaceVo.getGateType()) != EnterGateType.QR_READER){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "QR Reader 출입시스템 정보를 찾을 수 없습니다.");
    }
    return ImosQrGateManagerListResponse.builder()
        .wpId(workplaceVo.getWpId())
        .wpName(workplaceVo.getWpName())
        .attenendantList(entrGateWorkplaceDao.findQrGateManagerList(wpId))
        .build();
  }

  public ImosQrGateCoopWorkStatusResponse findCoopListByWorkingStatus(String wpId, int currentWorking){
    EntrGateWorkplaceVo workplaceVo = entrGateWorkplaceDao.findInfo(wpId);
    if( workplaceVo == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입시스템 정보를 찾을 수 없습니다.");
    }
    else if( EnterGateType.get(workplaceVo.getGateType()) != EnterGateType.QR_READER){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "QR Reader 출입시스템 정보를 찾을 수 없습니다.");
    }
    return ImosQrGateCoopWorkStatusResponse.builder()
        .workingStatus(currentWorking)
        .wpId(workplaceVo.getWpId())
        .wpName(workplaceVo.getWpName())
        .coopList(entrGateWorkplaceDao.findQrGateCoopListByWorkingStatus(wpId, currentWorking))
        .build();
  }

  public ImosQrGateCoopWorkingWorkerListResponse findCoopWorkerListByWorkingStatus(String wpId, int currentWorking, String coopMbId){
    EntrGateWorkplaceVo workplaceVo = entrGateWorkplaceDao.findInfo(wpId);
    if( workplaceVo == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입시스템 정보를 찾을 수 없습니다.");
    }
    else if( EnterGateType.get(workplaceVo.getGateType()) != EnterGateType.QR_READER){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "QR Reader 출입시스템 정보를 찾을 수 없습니다.");
    }
    G5MemberDto coop = g5MemberDao.findByMbId(coopMbId);
    if( coop == null || MemberLevel.get(coop.getMbLevel()) != MemberLevel.COOPERATIVE_COMPANY ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "협력사 정보가 존재하지 않습니다.");
    }
    return ImosQrGateCoopWorkingWorkerListResponse.builder()
        .workingStatus(currentWorking)
        .wpId(workplaceVo.getWpId())
        .wpName(workplaceVo.getWpName())
        .coopMbId(coop.getMbId())
        .coopMbName(coop.getName())
        .attenendantList(entrGateWorkplaceDao.findQrGateCoopWorkerListByWorkingStatus(wpId, currentWorking, coopMbId))
        .build();
  }

  public DefaultPageResult<AttendantVo> findGateEntranceHistoryPagingList(String wpId, EntergateWorkerHistoryPagingListRequest request){
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);

    List<Integer> workInMethods = new ArrayList<>();
    workInMethods.add(WorkInOutMethod.QRCODE.getCode());
    condition.put("workInMethods", workInMethods);

    condition.put("gateType", EnterGateType.QR_READER.getCode());

    return DefaultPageResult.<AttendantVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .list(findGateEntranceHistoryList(condition))
        .totalCount(entrGateWorkplaceDao.countGateEntranceHistoryList(condition))
        .build();
  }

  public List<AttendantVo> findGateEntranceHistoryList(Map<String,Object> condition){
    List<Integer> workInMethods = new ArrayList<>();
    workInMethods.add(WorkInOutMethod.QRCODE.getCode());
    condition.put("workInMethods", workInMethods);

    condition.put("gateType", EnterGateType.QR_READER.getCode());
    return entrGateWorkplaceDao.findGateEntranceHistoryList(condition);
  }
}
