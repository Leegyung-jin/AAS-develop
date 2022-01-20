package kr.co.hulan.aas.mvc.api.bls.service;

import com.google.common.base.CaseFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.properties.WorkplaceProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.bls.controller.request.BleMonitoringDataRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.request.BleMonitoringDataRequest.BLE_SMART_SEARCH_TYPE;
import kr.co.hulan.aas.mvc.api.bls.controller.request.SearchBleUserRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.request.SearchUserLocationRequest;
import kr.co.hulan.aas.mvc.api.bls.controller.response.BleMonitoringDataResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.BleUserVo;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoForBuildingResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoForFloorResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoForWorkplaceResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoWorkersBySensorResponse;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringWokspaceResponse;
import kr.co.hulan.aas.mvc.api.bls.dto.*;
import kr.co.hulan.aas.mvc.api.bookmark.service.BookmarkService;
import kr.co.hulan.aas.mvc.api.building.service.BuildingMgrService;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.dto.WorkplaceSummaryDto;
import kr.co.hulan.aas.mvc.api.monitor.service.CommonMonitoringService;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceService;
import kr.co.hulan.aas.mvc.dao.mapper.BlsMonitoringDao;
import kr.co.hulan.aas.mvc.dao.mapper.BuildingDao;
import kr.co.hulan.aas.mvc.dao.mapper.SensorInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import kr.co.hulan.aas.mvc.dao.repository.BookmarkBleRepository;
import kr.co.hulan.aas.mvc.model.domain.BookmarkBle;
import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import kr.co.hulan.aas.mvc.model.dto.BuildingFloorDto;
import kr.co.hulan.aas.mvc.model.dto.ImageAnalyticInfoDto;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import kr.co.hulan.aas.mvc.model.dto.SensorInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceMonitorConfigDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BlsMonitoringService {

  private Logger log = LoggerFactory.getLogger(BlsMonitoringService.class);

  @Autowired
  private WorkplaceProperties workplaceProperties;

  @Autowired
  private BlsMonitoringDao blsMonitoringDao;

  @Autowired
  private BuildingMgrService buildingMgrService;

  @Autowired
  private WorkplaceService workplaceService;

  @Autowired
  private SensorInfoDao sensorInfoDao;

  @Autowired
  CommonMonitoringService commonMonitoringService;

  @Autowired
  WorkPlaceWorkerDao workPlaceWorkerDao;

  @Autowired
  BuildingDao buildingDao;

  @Autowired
  BookmarkService bookmarkService;

  @Autowired
  private ModelMapper modelMapper;

  public MonitoringInfoForWorkplaceResponse findBuildingSituations(String wpId){
    Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
    conditionByUser.put("wpId", wpId);
    WorkPlaceDto dto = workplaceService.findById(wpId);

    MonitoringInfoForWorkplaceResponse res = modelMapper.map(dto, MonitoringInfoForWorkplaceResponse.class);
    res.setImageUrl(dto.getViewMapFileDownloadUrl());
    res.setMemo(workplaceProperties.getMemo(wpId));
    res.setBuildingSituationList(blsMonitoringDao.findBuildingSituations(conditionByUser));
    return res;
  }

  public MonitoringInfoForBuildingResponse findFloorSituations(Map<String,Object> condition){

    List<FloorSituation> floorSituations = blsMonitoringDao.findFloorSituations(condition);
    Map<Long, FloorSituationPerBuilding> map = new HashMap<Long,FloorSituationPerBuilding>();
    if( floorSituations != null && floorSituations.size() != 0 ){
      for( FloorSituation floorSituation : floorSituations ){
        FloorSituationPerBuilding buildingFloorS = map.get(floorSituation.getBuildingNo());
        if( buildingFloorS == null ){
          buildingFloorS = FloorSituationPerBuilding.builder()
              .buildingNo(floorSituation.getBuildingNo())
              .buildingName(floorSituation.getBuildingName())
              .floorUpstair(floorSituation.getFloorUpstair())
              .floorDownstair(floorSituation.getFloorDownstair())
              .crossSectionFile(floorSituation.getCrossSectionFile())
              .crossSectionFileDownloadUrl(floorSituation.getCrossSectionFileDownloadUrl())
              .build();
          map.put(floorSituation.getBuildingNo(), buildingFloorS );
        }
        buildingFloorS.addFloorSituation(floorSituation);
      }
    }
    return new MonitoringInfoForBuildingResponse(
        Arrays.asList(map.values().toArray(new FloorSituationPerBuilding[0]))
    );
  }

  public MonitoringInfoForFloorResponse findGridSituations(long building_no, Map<String,Object> condition){
    BuildingDto buildingDto = buildingMgrService.findById(building_no);
    List<FloorSituation> floorSituations = blsMonitoringDao.findFloorAll(condition);

    Map<Integer, FloorGridSituationPerFloor> map = new HashMap<Integer,FloorGridSituationPerFloor>();
    if( floorSituations != null && floorSituations.size() != 0 ){
      for( FloorSituation floorSituation : floorSituations ){
        FloorGridSituationPerFloor floor = map.get(floorSituation.getFloor());
        if( floor == null ){
          floor = FloorGridSituationPerFloor.builder()
                  .buildingNo(floorSituation.getBuildingNo())
                  .areaType(buildingDto.getAreaType())
                  .floor(floorSituation.getFloor())
                  .floorName(floorSituation.getFloorName())
                  .imageUrl(floorSituation.getViewFloorFileDownloadUrl())
                  .crossSectionGridX(floorSituation.getCrossSectionGridX())
                  .crossSectionGridY(floorSituation.getCrossSectionGridY())
                  .build();
          map.put(floor.getFloor(), floor);
        }
      }
      List<FloorGridSituation> floorGridSituations = blsMonitoringDao.findGridSituations(condition);
      if( floorGridSituations != null && floorGridSituations.size() != 0 ){
        for( FloorGridSituation floorGridSituation : floorGridSituations ){
          FloorGridSituationPerFloor floor = map.get(floorGridSituation.getFloor());
          if( floor != null ){
            floor.addFloorGridSituation(floorGridSituation);
          }
        }
      }
    }
    return MonitoringInfoForFloorResponse.builder()
        .buildingNo(buildingDto.getBuildingNo())
        .buildingName(buildingDto.getBuildingName())
        .areaType(buildingDto.getAreaType())
        .imageUrl(buildingDto.getViewFloorFileDownloadUrl())
        .crossSectionFileDownloadUrl(buildingDto.getCrossSectionFileDownloadUrl())
        .floorGridSituations(Arrays.asList(map.values().toArray(new FloorGridSituationPerFloor[0])))
        .build();

  }

  public MonitoringInfoWorkersBySensorResponse findCurrentWorkerListBySensor(int siIdx){
    SensorInfoDto dto = sensorInfoDao.findById(siIdx);
    if( dto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 센서입니다.");
    }

    Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
    conditionByUser.put("siIdx",siIdx);

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    MonitoringInfoWorkersBySensorResponse response = modelMapper.map(dto, MonitoringInfoWorkersBySensorResponse.class);
    response.setWorker_list(blsMonitoringDao.findCurrentWorkerListBySensor(conditionByUser));
    return response;
  }

  public Map<String,Object> findCurrentWorkerCountPerType(String wpId){
    return blsMonitoringDao.findCurrentWorkerCountPerType(wpId);
  }

  public SensorBuildingLocationDto findBleMonitoringWorerLocation(SearchUserLocationRequest request){
    SensorBuildingLocationDto locationDto = blsMonitoringDao.findDetectedUserSensorLocation(request.getConditionMap());
    if(locationDto == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "센서진입 정보가 존재하지 않는 사용자입니다.");
    }
    return locationDto;
  }

  public BleMonitoringDataResponse findBleMonitoringData(BleMonitoringDataRequest request){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);
    LocalTime start = LocalTime.now();

    if( request.getSearchType() == null ){
      if( StringUtils.isNotEmpty( request.getMarkerMbId())){
        request.setSearchType(BLE_SMART_SEARCH_TYPE.VIEW_FLOOR.getCode());
        SensorBuildingLocationDto locationDto = blsMonitoringDao.findDetectedUserSensorLocation(request.getConditionMap());
        if(locationDto == null ){
          throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "센서진입 정보가 존재하지 않는 사용자입니다.");
        }
        return findBleMonitoringData( BleMonitoringDataRequest.builder()
            .searchType(BLE_SMART_SEARCH_TYPE.VIEW_FLOOR.getCode())
            .wpId(locationDto.getWpId())
            .buildingNo(locationDto.getBuildingNo())
            .floor(locationDto.getFloor())
            .markerMbId(request.getMarkerMbId())
            .build());
      }
      else {
        request.setSearchType(BLE_SMART_SEARCH_TYPE.VIEW_MAP.getCode());
      }
    }
    WorkPlaceDto dto = workplaceService.findById(request.getWpId());
    if( dto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
    }
    BleMonitoringDataResponse res = BleMonitoringDataResponse.builder()
        .request(request)
        .build();

    Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
    conditionByUser.put("wpId", request.getWpId());

    BleMonitoringDataRequest.BLE_SMART_SEARCH_TYPE searchType = BleMonitoringDataRequest.BLE_SMART_SEARCH_TYPE.get(request.getSearchType());
    if( searchType == BLE_SMART_SEARCH_TYPE.VIEW_MAP ){
      res.setImageUrl( dto.getViewMapFileDownloadUrl() );
      res.setBleDataList(blsMonitoringDao.findBuildingSituations(conditionByUser));
    }
    else if( searchType == BLE_SMART_SEARCH_TYPE.CROSS_SECTION ){
      BuildingDto buildingDto = buildingMgrService.findById(request.getBuildingNo());
      if( buildingDto == null ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 건물입니다.");
      }
      res.setImageUrl( buildingDto.getCrossSectionFileDownloadUrl() );
      res.setBleDataList(blsMonitoringDao.findBuildingCrossSectionSituationForSmartMonitoring(request.getConditionMap()));

    }
    else if( searchType == BLE_SMART_SEARCH_TYPE.VIEW_FLOOR ){
      BuildingFloorDto floorDto = buildingMgrService.findFloor(request.getBuildingNo(), request.getFloor());
      if( floorDto == null ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 층입니다.");
      }
      res.setImageUrl( StringUtils.isNotEmpty(floorDto.getViewFloorFileDownloadUrl()) ?
          floorDto.getViewFloorFileDownloadUrl() :
          floorDto.getDefaultViewFloorFileDownloadUrl()
      );
      res.setBleDataList(blsMonitoringDao.findFloorSituationForSmartMonitoring(request.getConditionMap()));
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "지원하지 않는 검색 타입입니다.");
    }
    res.setFloorSituations(blsMonitoringDao.findWorkplaceSituationPerFloor(request.getWpId()));
    res.setCurrentWorkerCount( blsMonitoringDao.findCurrentWorkerCount(conditionByUser));
    // res.setWorkerCount( blsMonitoringDao.findCurrentWorkerCountPerType(request.getWpId()));
    Map<String,Object> commonResultInfo = commonMonitoringService.getCommonMonitoringInfo(dto);
    Map<String,Object> commonResultTransform = new HashMap<String,Object>();
    String[] keys = commonResultInfo.keySet().toArray(new String[0]);
    for(String key : keys ){
      commonResultTransform.put(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key.toLowerCase()), commonResultInfo.get(key));
    }
    ModelMapper mapper = new ModelMapper();
    mapper.map(commonResultTransform, res);
    return res;
  }

  public List<BleUserVo> searchBleUser(SearchBleUserRequest request){
    return blsMonitoringDao.searchBleUser(request.getConditionMap());
  }


  public List<AttendantSituationVo> findAttendantSituationListForMonitoring(Map<String, Object> conditionMap) {
    return blsMonitoringDao.findAttendantSituationListForMonitoring(conditionMap);
  }

  public List<AttendantVo> findAttendantListForMonitoring(Map<String, Object> conditionMap) {
    return blsMonitoringDao.findAttendantListForMonitoring(conditionMap);
  }

  public MonitoringWokspaceResponse findBleMonitoringWorkspace(String wpId){
    WorkPlaceDto dto = workplaceService.findById(wpId);
    if( dto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
    }
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);

    return MonitoringWokspaceResponse.builder()
        .workplace(modelMapper.map(dto, WorkplaceSummaryDto.class))
        .monitorConfig(modelMapper.map(dto, WorkPlaceMonitorConfigDto.class) )
        .buildingList(buildingDao.findListByCondition(condition))
        .build();
  }


}
