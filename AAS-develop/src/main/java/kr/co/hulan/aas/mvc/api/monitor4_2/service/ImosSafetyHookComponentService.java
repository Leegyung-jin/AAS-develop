package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookCoopStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookCurrentStateResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosSafetyHookWorkerListResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosSafetyHookCoopStateVo;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.mapper.SafeyHookDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImosSafetyHookComponentService {

  @Autowired
  private SafeyHookDao safeyHookDao;

  @Autowired
  private WorkPlaceDao workPlaceDao;

  @Autowired
  private G5MemberDao g5MemberDao;

  public ImosSafetyHookCurrentStateResponse findCurrentState(String wpId){
    return safeyHookDao.findCurrentState(wpId);
  }

  public ImosSafetyHookCoopStateResponse findCurrentCoopStateList(String wpId, int type, int state){

    WorkPlaceDto workplace = workPlaceDao.findById(wpId);
    if( workplace == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현장 정보가 존재하지 않습니다.");
    }

    List<ImosSafetyHookCoopStateVo> list = safeyHookDao.findCurrentCoopStateList(wpId, type, state);
    return ImosSafetyHookCoopStateResponse.builder()
        .eventType(type)
        .eventStatus(state)
        .wpId(workplace.getWpId())
        .wpName(workplace.getWpName())
        .coopStateList(list)
        .build();
  }


  public ImosSafetyHookWorkerListResponse findCurrentWorkerList(String wpId, int type, int state, String coopMbId){

    WorkPlaceDto workplace = workPlaceDao.findById(wpId);
    if( workplace == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현장 정보가 존재하지 않습니다.");
    }
    G5MemberDto coop = g5MemberDao.findByMbId(coopMbId);
    if( coop == null || MemberLevel.get(coop.getMbLevel()) != MemberLevel.COOPERATIVE_COMPANY ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "협력사 정보가 존재하지 않습니다.");
    }

    return ImosSafetyHookWorkerListResponse.builder()
        .eventType(type)
        .eventStatus(state)
        .wpId(workplace.getWpId())
        .wpName(workplace.getWpName())
        .coopMbId(coop.getMbId())
        .coopMbName(coop.getName())
        .coopStateList(safeyHookDao.findCurrentWorkers(wpId, type, state, coopMbId))
        .build();
  }
}
