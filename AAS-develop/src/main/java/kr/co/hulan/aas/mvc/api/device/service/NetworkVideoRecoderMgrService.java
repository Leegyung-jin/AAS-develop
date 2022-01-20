package kr.co.hulan.aas.mvc.api.device.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.infra.broker.KafkaEventProducer;
import kr.co.hulan.aas.infra.broker.vo.DefaultKafkaEventType;
import kr.co.hulan.aas.infra.broker.vo.NetworkVideoRecoderEventDto;
import kr.co.hulan.aas.infra.eis.EisServiceClient;
import kr.co.hulan.aas.mvc.api.device.controller.request.nvr.NetworkVideoRecoderCreateRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.nvr.NetworkVideoRecoderPagingListRequest;
import kr.co.hulan.aas.mvc.api.device.controller.request.nvr.NetworkVideoRecoderUpdateRequest;
import kr.co.hulan.aas.mvc.dao.mapper.NetworkVideoRecoderDao;
import kr.co.hulan.aas.mvc.dao.repository.NetworkVideoRecoderChannelRepository;
import kr.co.hulan.aas.mvc.dao.repository.NetworkVideoRecoderRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.nvr.NetworkVideoRecoder;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderChannelDto;
import kr.co.hulan.aas.mvc.model.dto.NetworkVideoRecoderDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NetworkVideoRecoderMgrService {

  private Logger logger = LoggerFactory.getLogger(NetworkVideoRecoderMgrService.class);

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private WorkPlaceRepository workPlaceRepository;

  @Autowired
  private NetworkVideoRecoderRepository networkVideoRecoderRepository;

  @Autowired
  private NetworkVideoRecoderChannelRepository networkVideoRecoderChannelRepository;

  @Autowired
  private NetworkVideoRecoderDao networkVideoRecoderDao;

  @Autowired
  private EisServiceClient eisServiceClient;

  @Autowired
  private KafkaEventProducer kafkaEventProducer;

  public DefaultPageResult<NetworkVideoRecoderDto> findListPage(
      NetworkVideoRecoderPagingListRequest request) {
    return DefaultPageResult.<NetworkVideoRecoderDto>builder()
        .currentPage(request.getPage())
        .pageSize(request.getPageSize())
        .totalCount(countListByCondition(request.getConditionMap()))
        .list(findListByCondition(request.getConditionMap()))
        .build();
  }

  public List<NetworkVideoRecoderDto> findListByCondition(Map<String,Object> conditionMap) {
    return networkVideoRecoderDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public Long countListByCondition(Map<String,Object> conditionMap) {
    return networkVideoRecoderDao.countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public NetworkVideoRecoderDto findInfo(long officeNo){
    NetworkVideoRecoderDto currentVo = networkVideoRecoderDao.findById(officeNo);
    if (currentVo != null ) {
      return currentVo;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }


  @Transactional("transactionManager")
  public Long create(NetworkVideoRecoderCreateRequest request) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    NetworkVideoRecoder saveEntity = modelMapper.map(request, NetworkVideoRecoder.class);
    if( networkVideoRecoderRepository.existsByIpAndPort(saveEntity.getIp(), saveEntity.getPort()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 IP 및 Port 로 등록된 NVR 이 존재합니다.");
    }

    if( !workPlaceRepository.findById(request.getWpId()).isPresent() ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현장 정보가 존재하지 않습니다.");
    }

    saveEntity.setCreator(loginUser.getMbId());
    saveEntity.setCreateDate(new Date());
    saveEntity.setUpdater(loginUser.getMbId());
    saveEntity.setUpdateDate(new Date());

    networkVideoRecoderRepository.save(saveEntity);
    sendEvent(DefaultKafkaEventType.CREATE, saveEntity);
    return saveEntity.getNvrNo();
  }

  @Transactional("transactionManager")
  public void update(NetworkVideoRecoderUpdateRequest request, long nvrNo) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    NetworkVideoRecoder saveEntity = networkVideoRecoderRepository.findById(nvrNo).orElse(null);
    if( saveEntity == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "NVR 정보가 존재하지 않습니다.");
    }

    if( networkVideoRecoderRepository.existsByIpAndPortAndNvrNoNot(saveEntity.getIp(), saveEntity.getPort(), saveEntity.getNvrNo()) ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 IP 및 Port 로 등록된 NVR 이 존재합니다.");
    }

    if( !workPlaceRepository.findById(request.getWpId()).isPresent() ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현장 정보가 존재하지 않습니다.");
    }

    modelMapper.map(request, saveEntity);
    saveEntity.setUpdater(loginUser.getMbId());
    saveEntity.setUpdateDate(new Date());

    networkVideoRecoderRepository.save(saveEntity);
    sendEvent(DefaultKafkaEventType.UPDATE, saveEntity);

  }


  @Transactional("transactionManager")
  public int delete(long nvrNo) {
    NetworkVideoRecoder saveEntity = networkVideoRecoderRepository.findById(nvrNo).orElse(null);
    if( saveEntity != null ){
      networkVideoRecoderRepository.delete(saveEntity);
      sendEvent(DefaultKafkaEventType.DELETE, saveEntity);
      return 1;
    }
    return 0;
  }

  public List<NetworkVideoRecoderChannelDto> findChannelList(long nvrNo){
    return networkVideoRecoderDao.findChannelList(nvrNo);
  }

  public List<NetworkVideoRecoderChannelDto> syncChannels(long nvrNo){
    if( !eisServiceClient.syncChannel(nvrNo) ){
      throw new CommonException(BaseCode.ERR_ETC_EXCEPTION.code(), "채널 동기화에 실패하였습니다.");
    }
    return findChannelList(nvrNo);
  }

  private void sendEvent(DefaultKafkaEventType type, NetworkVideoRecoder event){
    try{
      kafkaEventProducer.sendEventMessage(type, modelMapper.map(event, NetworkVideoRecoderEventDto.class));
    }
    catch(Exception e){
      logger.error(this.getClass().getSimpleName()+"|sendEvent|ERROR|"+type.getName()+"|"+event.toString(), e);
    }
  }
}
