package kr.co.hulan.aas.mvc.api.device.service;

import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.CctvKind;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.infra.broker.vo.NetworkVideoRecoderChEventDto;
import kr.co.hulan.aas.mvc.api.device.controller.request.WorkPlaceCctvCreateRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.WorkPlaceCctvListRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.WorkPlaceCctvUpdateRequest;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCctvDao;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceCctvRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.domain.WorkPlaceCctv;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCctvDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkPlaceCctvService {

  private Logger logger = LoggerFactory.getLogger(WorkPlaceCctvService.class);

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  Gson gson;

  @Autowired
  private WorkPlaceCctvDao workplaceCctvDao;

  @Autowired
  private WorkPlaceCctvRepository workPlaceCctvRepository;

  @Autowired
  private WorkPlaceRepository workPlaceRepository;

  public DefaultPageResult<WorkPlaceCctvDto> findListPage(WorkPlaceCctvListRequest request) {
    return DefaultPageResult.<WorkPlaceCctvDto>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .totalCount(findListCountByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<WorkPlaceCctvDto> findListByCondition(Map<String,Object> conditionMap) {
    return workplaceCctvDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long findListCountByCondition(Map<String,Object> conditionMap) {
    return workplaceCctvDao.countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }


  public WorkPlaceCctvDto findInfo(long cctvNo){
    WorkPlaceCctvDto dto = workplaceCctvDao.findInfo(cctvNo);
    if (dto != null ) {
      return dto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public WorkPlaceCctvDto create(WorkPlaceCctvCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    WorkPlace workplace = workPlaceRepository.findById(request.getWpId()).orElse(null);
    if ( workplace == null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 현장입니다.");
    }
    WorkPlaceCctv saveEntity = modelMapper.map(request, WorkPlaceCctv.class);
    saveEntity.setCctvKind( StringUtils.isNotBlank( saveEntity.getGid()) ?
        CctvKind.INTELLI_VIX.getCode()
        : CctvKind.NORMAL.getCode()
    );

    saveEntity.setCreator(loginUser.getUsername());
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdateDate(new Date());
    workPlaceCctvRepository.save(saveEntity);

    WorkPlaceCctvDto dto = modelMapper.map(saveEntity, WorkPlaceCctvDto.class);
    dto.setWpName(workplace.getWpName());
    return dto;
  }


  @Transactional("transactionManager")
  public void update(long cctvNo, WorkPlaceCctvUpdateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    WorkPlaceCctv saveEntity = workPlaceCctvRepository.findById(cctvNo).orElse(null);
    if ( saveEntity == null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현장 CCTV 정보를 찾을 수 없습니다.");
    }

    WorkPlace workplace = workPlaceRepository.findById(request.getWpId()).orElse(null);
    if ( workplace == null ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 현장입니다.");
    }

    modelMapper.map(request, saveEntity);
    saveEntity.setCctvKind( StringUtils.isNotBlank( saveEntity.getGid()) ?
        CctvKind.INTELLI_VIX.getCode()
        : CctvKind.NORMAL.getCode()
    );
    saveEntity.setUpdater(loginUser.getUsername());
    saveEntity.setUpdateDate(new Date());
    workPlaceCctvRepository.save(saveEntity);
  }


  @Transactional("transactionManager")
  public void delete(long cctvNo) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    WorkPlaceCctv saveEntity = workPlaceCctvRepository.findById(cctvNo).orElse(null);
    if( saveEntity != null ){
      workPlaceCctvRepository.delete(saveEntity);
    }

  }

  @Transactional("transactionManager")
  public void deleteMultiple(List<Long> cctvNoList) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    for (Long cctvNo : cctvNoList) {
      delete(cctvNo);
    }
  }

  @Transactional("transactionManager")
  public void receiveNvrChannelChangedEvent(NetworkVideoRecoderChEventDto eventVo, long eventTime){
    logger.info("receiveNvrChannelChangedEvent|"+eventVo);
    /*
    WorkPlaceCctv cctv = workPlaceCctvRepository.findByGid(eventVo.getGid());
    if( cctv != null ){
      cctv.setCctvUrl(eventVo.getUrl());
      cctv.setPtzStatus(eventVo.getIsPtz());
      cctv.setUpdateDate(new Date());
      cctv.setUpdater("System");
      workPlaceCctvRepository.save(cctv);
    }
     */
  }
}
