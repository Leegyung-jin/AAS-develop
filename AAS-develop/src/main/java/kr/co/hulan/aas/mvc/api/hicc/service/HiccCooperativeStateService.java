package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeAttendanceStatListRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeWorkplaceAttendanceStatExportRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.request.CooperativeWorkplaceAttendanceStatRequest;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCoopWorkplaceAttendanceStatResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCooperativeSectionStatResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccCooperativeStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeAttendanceStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.CooperativeStateVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.coop.SectionStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceAttendanceStatVo;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCoopDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiccCooperativeStateService {

  @Autowired
  private WorkPlaceCoopDao workPlaceCoopDao;

  @Autowired
  private WorkPlaceDao workPlaceDao;

  @Autowired
  private G5MemberDao g5MemberDao;

  public DefaultPageResult<CooperativeAttendanceStatVo>  findCooperativeAttendanceStatPagingList(CooperativeAttendanceStatListRequest request){
    Map<String,Object> condition = request.getConditionMap();
    return DefaultPageResult.<CooperativeAttendanceStatVo>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .list(findCooperativeAttendanceStatList(condition))
        .totalCount(countCooperativeAttendanceStatList(condition))
        .build();
  }

  public List<CooperativeAttendanceStatVo>  findCooperativeAttendanceStatList(Map<String,Object> condition){
    return workPlaceCoopDao.findCooperativeAttendanceStatList(condition);
  }

  public long  countCooperativeAttendanceStatList(Map<String,Object> condition){
    return workPlaceCoopDao.countCooperativeAttendanceStatList(condition);
  }

  public HiccCooperativeStateResponse findCooperativeState(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();

    CooperativeStateVo state = workPlaceCoopDao.findCooperativeState(condition);
    state.setWorkplaceCount(workPlaceDao.countSupportedWorkplaceByAccount(condition));

    condition.put("startRow", 0);
    condition.put("pageSize", 5);
    List<SectionStatVo> sectionList = workPlaceCoopDao.findCooperativeSectionStatList(condition);

    condition.put("pageSize", 5);
    List<CooperativeAttendanceStatVo>  attrStatList = workPlaceCoopDao.findCooperativeAttendanceStatList(condition);

    return HiccCooperativeStateResponse.builder()
        .coopState(state)
        .sectionList(sectionList)
        .coopList(attrStatList)
        .build();
  }

  public HiccCooperativeSectionStatResponse findCooperativeSectionStat(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    return HiccCooperativeSectionStatResponse.builder()
        .sectionList(workPlaceCoopDao.findCooperativeSectionStatList(condition))
        .build();
  }

  public HiccCoopWorkplaceAttendanceStatResponse findCooperativeWorkplaceAttendanceStatPagingList(String coopMbId, CooperativeWorkplaceAttendanceStatRequest request){
    G5MemberDto coop = g5MemberDao.findByMbId(coopMbId);
    if( coop != null && MemberLevel.get(coop.getMbLevel()) == MemberLevel.COOPERATIVE_COMPANY ){
      Map<String,Object> condition = request.getConditionMap();
      condition.put("coopMbId", coopMbId);

      return HiccCoopWorkplaceAttendanceStatResponse.builder()
          .coopMbId(coop.getMbId())
          .coopMbName(coop.getName())
          .currentPage(request.getPage())
          .pageSize(request.getPageSize())
          .totalCount(workPlaceCoopDao.countWorkplaceAttendanceStatListByCoop(condition))
          .list(workPlaceCoopDao.findWorkplaceAttendanceStatListByCoop(condition))
          .build();
    }
    else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }


  public List<HiccWorkplaceAttendanceStatVo> findCooperativeWorkplaceAttendanceStatList(String coopMbId, CooperativeWorkplaceAttendanceStatExportRequest request){
    G5MemberDto coop = g5MemberDao.findByMbId(coopMbId);
    if( coop != null && MemberLevel.get(coop.getMbLevel()) == MemberLevel.COOPERATIVE_COMPANY ){
      Map<String,Object> condition = request.getConditionMap();
      condition.put("coopMbId", coopMbId);

      return workPlaceCoopDao.findWorkplaceAttendanceStatListByCoop(condition);
    }
    else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }
}
