package kr.co.hulan.aas.mvc.api.gps.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.gps.controller.request.CreateGeofenceGroupRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.ListGeofenceGroupRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.UpdateGeofenceGroupRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.ListGeofenceGroupResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.GpsFenceGroupResponse;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceService;
import kr.co.hulan.aas.mvc.dao.mapper.GpsCheckPolicyInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkGeofenceInfoDao;
import kr.co.hulan.aas.mvc.dao.repository.WorkGeofenceGroupRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkGeofenceInfoRepository;
import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceGroup;
import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceGroupKey;
import kr.co.hulan.aas.mvc.model.domain.WorkGeofenceInfo;
import kr.co.hulan.aas.mvc.model.dto.GpsCheckPolicyInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkGeofenceGroupDto;
import kr.co.hulan.aas.mvc.model.dto.WorkGeofenceInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GpsFenceService {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  WorkplaceService workplaceService;

  @Autowired
  WorkGeofenceInfoDao workGeofenceInfoDao;

  @Autowired
  GpsCheckPolicyInfoDao gpsCheckPolicyInfoDao;

  @Autowired
  WorkGeofenceGroupRepository workGeofenceGroupRepository;

  @Autowired
  WorkGeofenceInfoRepository workGeofenceInfoRepository;

  public ListGeofenceGroupResponse findGroupListPage(ListGeofenceGroupRequest request) {
    return new ListGeofenceGroupResponse(
        countGroupListByCondition(request.getConditionMap()),
        findGroupListByCondition(request.getConditionMap())
    );
  }

  public List<WorkGeofenceGroupDto> findGroupListByCondition(Map<String,Object> conditionMap) {
    return workGeofenceInfoDao.findGroupListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long countGroupListByCondition(Map<String,Object> conditionMap) {
    return workGeofenceInfoDao.countGroupListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }


  public GpsFenceGroupResponse findWorkplaceFenceGroupList(String wpId){
    GpsCheckPolicyInfoDto policy = gpsCheckPolicyInfoDao.findByWpId(wpId);
    if( policy == null ){
      throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "GPS 정책이 존재하지 않습니다.");
    }
    WorkPlaceDto workPlaceDto = workplaceService.findById(wpId);
    if( workPlaceDto == null ){
      throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "현장 정보가 존재하지 않습니다.");
    }

    List<WorkGeofenceGroupDto> groupList = workGeofenceInfoDao.findGeofenceGroupListByWorkplace(wpId);
    return GpsFenceGroupResponse.builder()
        .totalCount(groupList != null ? groupList.size() : 0)
        .gpsCenterLat(workPlaceDto.getGpsCenterLat())
        .gpsCenterLong(workPlaceDto.getGpsCenterLong())
        .groupList(groupList)
        .build();
  }


  public WorkGeofenceGroupDto findGroupInfo(String wpId, int wpSeq){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("wpSeq", wpSeq);
    return workGeofenceInfoDao.findGroupById(condition);
  }

  @Transactional("transactionManager")
  public int createFenceGroup(CreateGeofenceGroupRequest request){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    WorkPlaceDto workPlaceDto = workplaceService.findById(request.getWpId());
    if( workPlaceDto == null ){
      throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "현장 정보가 존재하지 않습니다.");
    }

    WorkGeofenceGroup fenceGroup = modelMapper.map(request, WorkGeofenceGroup.class);
    fenceGroup.setCreator(loginUser.getMbId());
    fenceGroup.setCreateDate(new Date());
    fenceGroup.setUpdater(loginUser.getMbId());
    fenceGroup.setUpdateDate(new Date());

    Integer maxWpSeq = workGeofenceGroupRepository.getMaxWpSeqByWpId(fenceGroup.getWpId());
    int wpSeq = maxWpSeq == null ? 0 : maxWpSeq + 1;
    fenceGroup.setWpSeq(wpSeq);
    workGeofenceGroupRepository.save(fenceGroup);

    List<WorkGeofenceInfoDto> fenceLocations = request.getFenceLocations();
    List<WorkGeofenceInfo> fenceList = new ArrayList<WorkGeofenceInfo>();
    int seq = 1;
    for(WorkGeofenceInfoDto fenceDto : fenceLocations ){
      WorkGeofenceInfo fenceInfo = modelMapper.map(fenceDto, WorkGeofenceInfo.class);
      fenceInfo.setWpId(fenceGroup.getWpId());
      fenceInfo.setWpSeq(fenceGroup.getWpSeq());
      fenceInfo.setSeq(seq++);
      fenceInfo.setUpdDatetime(new Date());
      fenceInfo.setUpdater(loginUser.getMbId());
      fenceList.add(fenceInfo);
    }
    workGeofenceInfoRepository.saveAll(fenceList);
    return wpSeq;
  }


  @Transactional("transactionManager")
  public void updateFenceGroup(String wpId, int wpSeq, UpdateGeofenceGroupRequest request){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    WorkPlaceDto workPlaceDto = workplaceService.findById(wpId);
    if( workPlaceDto == null ){
      throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "현장 정보가 존재하지 않습니다.");
    }

    WorkGeofenceGroup fenceGroup = workGeofenceGroupRepository.findById( WorkGeofenceGroupKey.builder()
            .wpId(wpId)
            .wpSeq(wpSeq)
            .build()
    ).orElse(null);

    if( fenceGroup == null ){
      throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "Fence 정보가 존재하지 않습니다.");
    }

    modelMapper.map(request, fenceGroup);
    fenceGroup.setUpdater(loginUser.getMbId());
    fenceGroup.setUpdateDate(new Date());
    workGeofenceGroupRepository.save(fenceGroup);

    workGeofenceInfoRepository.deleteAllByWpIdAndWpSeq(fenceGroup.getWpId(), fenceGroup.getWpSeq());
    workGeofenceInfoRepository.flush();

    List<WorkGeofenceInfoDto> fenceLocations = request.getFenceLocations();
    List<WorkGeofenceInfo> fenceList = new ArrayList<WorkGeofenceInfo>();
    int seq = 1;
    for(WorkGeofenceInfoDto fenceDto : fenceLocations ){
      WorkGeofenceInfo fenceInfo = modelMapper.map(fenceDto, WorkGeofenceInfo.class);
      fenceInfo.setWpId(fenceGroup.getWpId());
      fenceInfo.setWpSeq(fenceGroup.getWpSeq());
      fenceInfo.setSeq(seq++);
      fenceInfo.setUpdDatetime(new Date());
      fenceInfo.setUpdater(loginUser.getMbId());
      fenceList.add(fenceInfo);
    }
    workGeofenceInfoRepository.saveAll(fenceList);

  }

  @Transactional("transactionManager")
  public void delete(String wpId, int wpSeq){
    WorkGeofenceGroup fenceGroup = workGeofenceGroupRepository.findById( WorkGeofenceGroupKey.builder()
        .wpId(wpId)
        .wpSeq(wpSeq)
        .build()
    ).orElse(null);

    if( fenceGroup != null ){
      workGeofenceInfoRepository.deleteAllByWpIdAndWpSeq(fenceGroup.getWpId(), fenceGroup.getWpSeq());
      workGeofenceGroupRepository.delete(fenceGroup);
    }
  }

  @Transactional("transactionManager")
  public void deleteMultiple(List<String> geoIds){
    List<WorkGeofenceGroupKey> keyList = geoIds.stream()
        .map( geoId -> WorkGeofenceGroupKey.builder()
            .wpId(StringUtils.substringBefore(geoId, "_"))
            .wpSeq(NumberUtils.toInt(StringUtils.substringAfter(geoId, "_")))
            .build()
        ).collect(Collectors.toList());
    for( WorkGeofenceGroupKey key : keyList ){
      delete(key.getWpId(), key.getWpSeq());
    }
  }
}
