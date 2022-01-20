package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.HulanComponentSiteType;
import kr.co.hulan.aas.common.code.NvrEventActionMethod;
import kr.co.hulan.aas.common.code.NvrEventType;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.code.dto.CodeDto;
import kr.co.hulan.aas.mvc.api.hicc.vo.ImosNoticeVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosIntelliVixEventConfirmRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosIntelliVixEventMultiConfirmRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosIntelliVixEventPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosNoticePagingRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.WorkplaceNvrMonitorConfigUpdateRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosIntelliVixMainResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.NvrEventDetailVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.NvrEventSummaryVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr.WorkplaceNvrMonitorConfigVo;
import kr.co.hulan.aas.mvc.dao.mapper.NetworkVideoRecoderDao;
import kr.co.hulan.aas.mvc.dao.mapper.NvrEventDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceMonitorConfigDao;
import kr.co.hulan.aas.mvc.dao.repository.NvrEventRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceMonitorConfigRepository;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceMonitorConfig;
import kr.co.hulan.aas.mvc.model.domain.nvr.NvrEvent;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeDto;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderChannelDto;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderDto;
import kr.co.hulan.aas.mvc.model.dto.NvrEventDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceMonitorConfigDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImosIntelliVixComponentService {

  private Logger logger = LoggerFactory.getLogger(ImosIntelliVixComponentService.class);

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private NvrEventDao nvrEventDao;

  @Autowired
  private NetworkVideoRecoderDao networkVideoRecoderDao;

  @Autowired
  private NvrEventRepository nvrEventRepository;

  @Autowired
  private WorkPlaceMonitorConfigDao workPlaceMonitorConfigDao;

  @Autowired
  private WorkPlaceMonitorConfigRepository workPlaceMonitorConfigRepository;

  public ImosIntelliVixMainResponse findMainInfoById(String wpId){
    return ImosIntelliVixMainResponse.builder()
        .config(findWorkplaceNvrConfig(wpId))
        .nvrList(findNvrListByWpId(wpId))
        .build();
  }

  public List<NetworkVideoRecoderDto> findNvrListByWpId(String wpId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId",wpId);
    return networkVideoRecoderDao.findListByCondition(condition);
  }

  public List<NetworkVideoRecoderChannelDto> findNvrChannelListByWpId(String wpId){
    return networkVideoRecoderDao.findChannelListByWpId(wpId);
  }

  public DefaultPageResult<NvrEventSummaryVo> findListPage(ImosIntelliVixEventPagingListRequest request) {
    return DefaultPageResult.<NvrEventSummaryVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .totalCount(findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .list(findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())))
        .build();
  }

  public List<NvrEventSummaryVo> findListByCondition(Map<String,Object> conditionMap) {
    return nvrEventDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return nvrEventDao.countListByCondition(conditionMap);
  }

  public NvrEventDetailVo findDetailInfo(long eventNo){
    NvrEventDetailVo dto = nvrEventDao.findById(eventNo);
    if (dto != null ) {
      return dto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void confirmTreatment(long eventNo, ImosIntelliVixEventConfirmRequest request){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    NvrEvent nvrEvent = nvrEventRepository.findById(eventNo).orElse(null);
    if( nvrEvent == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "이벤트 정보가 존재하지 않습니다.");
    }
    EnableCode actionStatus = EnableCode.get(nvrEvent.getActionStatus());
    if( actionStatus == EnableCode.DISABLED ){
      nvrEvent.setActionStatus(EnableCode.ENABLED.getCode());
      nvrEvent.setActionEndDate(new Date());
      nvrEvent.setActionEndTreator(loginUser.getMbId());
    }
    modelMapper.map(request, nvrEvent);
    nvrEvent.setUpdateDate(new Date());
    nvrEvent.setUpdater(loginUser.getMbId());
    nvrEventRepository.save(nvrEvent);
  }

  @Transactional("transactionManager")
  public void confirmTreatment(ImosIntelliVixEventMultiConfirmRequest request){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    for( Long eventNo : request.getEventNoList() ){
      NvrEvent nvrEvent = nvrEventRepository.findById(eventNo).orElse(null);
      if( nvrEvent == null ){
        throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "["+eventNo+"] 이벤트 정보가 존재하지 않습니다.");
      }
      EnableCode actionStatus = EnableCode.get(nvrEvent.getActionStatus());
      if( actionStatus == EnableCode.DISABLED ){
        nvrEvent.setActionStatus(EnableCode.ENABLED.getCode());
        nvrEvent.setActionEndDate(new Date());
        nvrEvent.setActionEndTreator(loginUser.getMbId());
      }
      nvrEvent.setActionMethod(request.getActionMethod());
      nvrEvent.setUpdateDate(new Date());
      nvrEvent.setUpdater(loginUser.getMbId());
      nvrEventRepository.save(nvrEvent);
    }
  }

  public List<CodeDto> findEventTypeCode(){
    List<CodeDto> list = new ArrayList<CodeDto>();
    for( NvrEventType type : NvrEventType.values()){
      list.add(new CodeDto(type.getCode(), type.getExposeName()));
    }
    return list;
  }

  public List<CodeDto> findEventActionMethodCode(){
    List<CodeDto> list = new ArrayList<CodeDto>();
    for( NvrEventActionMethod type : NvrEventActionMethod.values()){
      list.add(new CodeDto(""+type.getCode(), type.getName()));
    }
    return list;
  }

  public WorkplaceNvrMonitorConfigVo findWorkplaceNvrConfig(String wpId){

    WorkPlaceMonitorConfigDto config =  workPlaceMonitorConfigDao.findById(wpId);
    if( config != null ){
      return modelMapper.map(config,WorkplaceNvrMonitorConfigVo.class );
    }
    return new WorkplaceNvrMonitorConfigVo();

  }

  @Transactional("transactionManager")
  public void updateWorkplaceNvrConfig(String wpId, WorkplaceNvrMonitorConfigUpdateRequest request ){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    WorkPlaceMonitorConfig config =  workPlaceMonitorConfigRepository.findById(wpId).orElse(null);
    if( config != null ){
      modelMapper.map(request,config );
    }
    else {
      config = modelMapper.map(request,WorkPlaceMonitorConfig.class );
      config.prePersist();
    }
    config.setUpdateDate(new Date());
    config.setUpdater(loginUser.getMbId());
    workPlaceMonitorConfigRepository.save(config);
  }


}
