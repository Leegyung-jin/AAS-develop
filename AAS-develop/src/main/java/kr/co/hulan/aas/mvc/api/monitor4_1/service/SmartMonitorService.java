package kr.co.hulan.aas.mvc.api.monitor4_1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.CommuteType;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.MeasureEnvironmentType;
import kr.co.hulan.aas.common.code.WorkerCheckType;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.infra.weather.WeatherInfo;
import kr.co.hulan.aas.mvc.api.accident.service.FallingAccidentService;
import kr.co.hulan.aas.mvc.api.bls.controller.response.BleUserVo;
import kr.co.hulan.aas.mvc.api.bls.controller.response.MonitoringInfoWorkersBySensorResponse;
import kr.co.hulan.aas.mvc.api.bls.service.BlsMonitoringService;
import kr.co.hulan.aas.mvc.api.board.service.NoticeBoardService;
import kr.co.hulan.aas.mvc.api.building.service.BuildingMgrService;
import kr.co.hulan.aas.mvc.api.device.service.WorkPlaceCctvService;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
import kr.co.hulan.aas.mvc.api.gas.service.GasService;
import kr.co.hulan.aas.mvc.api.gps.service.GpsFenceService;
import kr.co.hulan.aas.mvc.api.gps.service.GpsService;
import kr.co.hulan.aas.mvc.api.monitor.controller.request.AnalyticInfoMgrRequest;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleCrossSectionDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleFloorDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.BleViewMapDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.GpsDataResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.GpsFenceGroupResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceMainResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringResponse;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceUiComponentResponse;
import kr.co.hulan.aas.mvc.api.monitor.dto.EnvironmentMeasureDeviceVo;
import kr.co.hulan.aas.mvc.api.monitor.dto.EnvironmentMeasureVo;
import kr.co.hulan.aas.mvc.api.monitor.dto.GpsObjectVo;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerHistoryPagingListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.EntergateWorkerListRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.MonitorMainRequest;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.EntergateMemberStatResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.HazardStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.OpeEquipmentStatusResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.WatchOutResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.response.WorkStatusSummaryResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CommuteTypeSummary;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CurrentWorkStatusSummary;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateStatInfo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateSystemInfo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.OpeEquipmentVo;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkPlaceUiComponentService;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkerCheckService;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceService;
import kr.co.hulan.aas.mvc.dao.mapper.AirEnvironmentDao;
import kr.co.hulan.aas.mvc.dao.mapper.BlsMonitoringDao;
import kr.co.hulan.aas.mvc.dao.mapper.EntrGateWorkplaceDao;
import kr.co.hulan.aas.mvc.dao.mapper.GasLogDao;
import kr.co.hulan.aas.mvc.dao.mapper.GpsLocationDao;
import kr.co.hulan.aas.mvc.dao.mapper.GpsMonitoringDao;
import kr.co.hulan.aas.mvc.dao.mapper.ImageAnalyticInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.SensorInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogInoutDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkEquipmentInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkerCheckDao;
import kr.co.hulan.aas.mvc.dao.repository.FallingAccidentRecentRepository;
import kr.co.hulan.aas.mvc.dao.repository.GasLogRecentRepository;
import kr.co.hulan.aas.mvc.dao.repository.ImageAnalyticInfoRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkDeviceInfoRepository;
import kr.co.hulan.aas.mvc.model.domain.FallingAccidentRecent;
import kr.co.hulan.aas.mvc.model.domain.GasLogRecent;
import kr.co.hulan.aas.mvc.model.domain.ImageAnalyticInfo;
import kr.co.hulan.aas.mvc.model.domain.ImageAnalyticInfoKey;
import kr.co.hulan.aas.mvc.model.domain.WorkDeviceInfo;
import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import kr.co.hulan.aas.mvc.model.dto.BuildingFloorDto;
import kr.co.hulan.aas.mvc.model.dto.GasLogDto;
import kr.co.hulan.aas.mvc.model.dto.GasSafeRangeDto;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import kr.co.hulan.aas.mvc.model.dto.SensorInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceCctvDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import kr.co.hulan.aas.mvc.model.dto.WorkerCheckDto;
import kr.co.hulan.aas.mvc.service.weather.WeatherService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SmartMonitorService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private WorkplaceService workplaceService;

  @Autowired
  private WorkPlaceUiComponentService workPlaceUiComponentService;

  @Autowired
  private NoticeBoardService noticeBoardService;

  @Autowired
  private BuildingMgrService buildingMgrService;

  @Autowired
  WeatherService weatherService;

  @Autowired
  FallingAccidentService fallingAccidentService;

  @Autowired
  private GpsFenceService gpsFenceService;

  @Autowired
  private WorkerCheckService workerCheckService;

  @Autowired
  private GpsService gpsService;

  @Autowired
  private GasService gasService;

  @Autowired
  private BlsMonitoringService blsMonitoringService;

  @Autowired
  private GpsLocationDao gpsLocationDao;

  @Autowired
  private GpsMonitoringDao gpsMonitoringDao;

  @Autowired
  private ImageAnalyticInfoDao imageAnalyticInfoDao;

  @Autowired
  BlsMonitoringDao blsMonitoringDao;

  @Autowired
  SensorLogInoutDao sensorLogInoutDao;

  @Autowired
  private SensorInfoDao sensorInfoDao;

  @Autowired
  WorkPlaceWorkerDao workPlaceWorkerDao;;

  @Autowired
  WorkerCheckDao workerCheckDao;;

  @Autowired
  WorkEquipmentInfoDao workEquipmentInfoDao;

  @Autowired
  AirEnvironmentDao airEnvironmentDao;

  @Autowired
  EntrGateWorkplaceDao entrGateWorkplaceDao;

  @Autowired
  FallingAccidentRecentRepository fallingAccidentRecentRepository;

  @Autowired
  GasLogDao gasLogDao;

  @Autowired
  WorkDeviceInfoRepository workDeviceInfoRepository;

  @Autowired
  GasLogRecentRepository gasLogRecentRepository;

  @Autowired
  ImageAnalyticInfoRepository imageAnalyticInfoRepository;

  @Autowired
  private WorkPlaceCctvService workPlaceCctvService;

  public WorkplaceSupportMonitoringResponse findAvailableMonitoringList(){
    return workplaceService.findAvailableMonitoringList();
  }

  public WorkplaceMainResponse findWorkplaceMainInfo(String wpId, MonitorMainRequest request){
    WorkplaceMainResponse res = new WorkplaceMainResponse();
    if( request.isWorkplace() ){
      res.setMonitoringWorkplace(workplaceService.findWorkplaceSupportMonitoringInfo(wpId));
    }
    if( request.isUiComponent() ){
      res.setUiComponentList(workPlaceUiComponentService.findListByWpId(wpId));
    }
    if( request.isWeather() ){
      res.setWeather(weatherService.getWeatherInfo(wpId));
    }
    if( request.isNotice() ){
      res.setNotice(noticeBoardService.findLastNoticeByWpId(wpId));
    }
    if( request.isDust() ){
      res.setDust(airEnvironmentDao.findInfoByWpId(wpId));
    }
    return res;
  }

  public WeatherInfo findWorkplaceWeather(String wpId){
    WeatherInfo info = weatherService.getWeatherInfo(wpId);
    if( info == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
    return info;
  }


  public WorkplaceUiComponentResponse findWorkplaceUiComponent(String wpId, int location){
    return WorkplaceUiComponentResponse.builder()
        .uiComponent(workPlaceUiComponentService.findById(wpId, location))
        .supportedUiComponents(workPlaceUiComponentService.findSupportedListById(wpId, location))
        .build();
  }

  @Transactional("transactionManager")
  public void assignWorkplaceUiComponent(String wpId, int location, String cmptId){
    workPlaceUiComponentService.assignWorkplaceUiComponent(wpId, location, cmptId);
  }

  @Transactional("transactionManager")
  public void unassignWorkplaceUiComponent(String wpId, int location){
    workPlaceUiComponentService.unassignWorkplaceUiComponent(wpId, location);
  }

  public GpsDataResponse getGpsMonitoringData(String wpId, String markerMbId){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    if( StringUtils.isNotBlank(markerMbId)){
      condition.put("markerMbId", markerMbId);
    }

    // 장비 맵핑되지 않은 근로자
    condition.put("EQUIPMENT", "NOT_EQUIP");
    List<GpsObjectVo> workerList = gpsLocationDao.findGpsLocationWorker(condition);

    // 장비기사
    condition.put("EQUIPMENT", "EQUIP");
    List<GpsObjectVo>  driverList = gpsLocationDao.findGpsLocationWorker(condition);

    List<GpsObjectVo>  equipementList = gpsLocationDao.findGpsLocationEquipment(condition);

    return GpsDataResponse.builder()
        .workers(workerList)
        .drivers(driverList)
        .equipments(equipementList)
        .totalWorkerCount(sensorLogInoutDao.getWorkCount(condition))
        .currentWorkerCount(gpsMonitoringDao.findCurrentWorkerCount(wpId))
        .build();
  }

  public BleViewMapDataResponse getBleViewMapMonitoringData(String wpId){
    WorkPlaceDto dto = workplaceService.findById(wpId);
    if( dto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
    }
    Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
    conditionByUser.put("wpId", wpId);

    return BleViewMapDataResponse.builder()
        .imageUrl(dto.getViewMapFileDownloadUrl())
        .buildingSituationList(blsMonitoringDao.findBuildingSituations(conditionByUser))
        .totalWorkerCount(sensorLogInoutDao.getWorkCount(conditionByUser))
        .currentWorkerCount(blsMonitoringDao.findCurrentWorkerCount(conditionByUser))
        .build();
  }

  public BleCrossSectionDataResponse getBleCrossSectionMonitoringData(String wpId, long buildingNo){

    BuildingDto buildingDto = buildingMgrService.findById(buildingNo);
    if( buildingDto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 빌딩입니다.");
    }
    Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
    conditionByUser.put("wpId", wpId);
    conditionByUser.put("buildingNo", buildingNo);
    return BleCrossSectionDataResponse.builder()
        .imageUrl(buildingDto.getCrossSectionFileDownloadUrl())
        .buildingNo(buildingDto.getBuildingNo())
        .buildingName(buildingDto.getBuildingName())
        .floorSituationList(blsMonitoringDao.findBuildingCrossSectionSituationForSmartMonitoring(conditionByUser))
        .totalWorkerCount(sensorLogInoutDao.getWorkCount(conditionByUser))
        .currentWorkerCount(blsMonitoringDao.findCurrentWorkerCount(conditionByUser))
        .build();
  }

  public BleFloorDataResponse getBleFloorMonitoringData(String wpId, long buildingNo, int floor, String marker){

    BuildingDto buildingDto = buildingMgrService.findById(buildingNo);

    BuildingFloorDto floorDto = buildingMgrService.findFloor(buildingNo, floor);
    if( floorDto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 층입니다.");
    }
    String floorFileUrl = floorDto.getViewFloorFileDownloadUrl();
    if( StringUtils.isEmpty( floorFileUrl ) ){
      floorFileUrl = floorDto.getDefaultViewFloorFileDownloadUrl();
    }
    Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
    conditionByUser.put("wpId", wpId);
    conditionByUser.put("buildingNo", buildingNo);
    conditionByUser.put("floor", floor);

    if( StringUtils.isNotBlank(marker)){
      conditionByUser.put("markerMbId", marker);
    }
    return BleFloorDataResponse.builder()
        .imageUrl(floorFileUrl)
        .totalFloor(buildingDto.getTotalFloor())
        .buildingNo(buildingDto.getBuildingNo())
        .buildingName(buildingDto.getBuildingName())
        .floor(floor)
        .floorName(floorDto.getFloorName())
        .areaType(buildingDto.getAreaType())
        .oneStoriedFloor(buildingDto.getOneStoriedFloor())
        .floorSensorSituationList(blsMonitoringDao.findFloorSituationForSmartMonitoring(conditionByUser))
        .totalWorkerCount(sensorLogInoutDao.getWorkCount(conditionByUser))
        .currentWorkerCount(blsMonitoringDao.findCurrentWorkerCount(conditionByUser))
        .build();
  }

  public GpsFenceGroupResponse findWorkplaceFenceGroupList(String wpId){
    return gpsFenceService.findWorkplaceFenceGroupList(wpId);
  }


  public List<BleUserVo> searchBleUser(String wpId, String mbName){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    if( StringUtils.isNotBlank(mbName)){
      condition.put("mbName", mbName);
    }
    return blsMonitoringDao.searchBleUser(condition);
  }


  public SensorBuildingLocationDto findBleMonitoringWorerLocation(String wpId, String mbId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("mbId", mbId);
    SensorBuildingLocationDto locationDto = blsMonitoringDao.findDetectedUserSensorLocation(condition);
    if(locationDto == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "센서진입 정보가 존재하지 않는 사용자입니다.");
    }
    return locationDto;
  }

  public MonitoringInfoWorkersBySensorResponse findCurrentWorkerListBySensor(int siIdx){
    SensorInfoDto dto = sensorInfoDao.findById(siIdx);
    if( dto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 센서입니다.");
    }

    Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
    conditionByUser.put("siIdx", siIdx);

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    MonitoringInfoWorkersBySensorResponse response = modelMapper.map(dto, MonitoringInfoWorkersBySensorResponse.class);
    response.setWorker_list(blsMonitoringDao.findCurrentWorkerListBySensor(conditionByUser));
    return response;
  }

  public WatchOutResponse findWatchOutInfo(String wpId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    //condition.put("startRow", 0);
    //condition.put("pageSize", 10);
    return WatchOutResponse.builder()
        .dangerAreaWorkers(workerCheckDao.findDangerAreaWorkerList(condition))
        .highRiskWorkers(workerCheckDao.findCheckWorkerList(condition))
        .fallingAccidentWorkers(fallingAccidentService.findFallingAccidentListByWpId(wpId))
        .imageAnalyticInfos(imageAnalyticInfoDao.findList(condition))
        .build();
  }

  @Transactional("transactionManager")
  public void confirmFallingAccident(String wpId, String mbId){
    FallingAccidentRecent recent = fallingAccidentRecentRepository.findById(mbId).orElse(null);
    if( recent != null ){
      recent.setDashboardPopup(EnableCode.DISABLED.getCode());
      fallingAccidentRecentRepository.save(recent);
    }
  }

  @Transactional("transactionManager")
  public void deleteFallingAccident(String wpId, String mbId){
    FallingAccidentRecent recent = fallingAccidentRecentRepository.findById(mbId).orElse(null);
    if( recent != null ){
      fallingAccidentRecentRepository.delete(recent);
    }
  }


  public List<WorkerCheckDto> findHighRiskWorkers(String wpId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("wcType", WorkerCheckType.HIGH_RISK.getCode());
    return workerCheckService.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(condition));
  }


  @Deprecated
  public WorkStatusSummaryResponse findBleWorkStatusSummary(String wpId){
    Map<String,Object> conditionByUser = AuthenticationHelper.addAdditionalConditionByLevel();
    conditionByUser.put("wpId", wpId);

    CommuteTypeSummary commuteTypeSummary = workPlaceWorkerDao.findCommuteTypeSummary(wpId);
    long currentWorkerCount = blsMonitoringDao.findCurrentWorkerCount(conditionByUser);

    return WorkStatusSummaryResponse.builder()
        .commuteSummary(commuteTypeSummary)
        .currentWorkStatus(CurrentWorkStatusSummary.builder()
            .totalWorkerCount(commuteTypeSummary.getTotalWorkerCount())
            .currentWorkerCount(currentWorkerCount)
            .build())
        .build();
  }

  @Deprecated
  public List<AttendantVo> findWorkerListByCommuteType(String wpId, String commuteType ){
    CommuteType cmType = CommuteType.get(commuteType);
    if( cmType == CommuteType.QR_ENTER ){
      return workPlaceWorkerDao.findQrEnterCommuteWorker(wpId);
    }
    else if( cmType == CommuteType.APP_ENTER ){
      return workPlaceWorkerDao.findAppEnterCommuteWorker(wpId);
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "지원하지 않는 출근 유형입니다. ["+commuteType+"]");
    }
  }

  @Deprecated
  public List<AttendantVo> findCurrentEntranceWorkerList(String wpId, String monitorType, Integer currentWorking){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("monitorType", monitorType);
    condition.put("currentWorking", currentWorking);
    return workPlaceWorkerDao.findCurrentEntranceWorkerList(condition);
  }


  @Deprecated
  public WorkStatusSummaryResponse findGpsWorkStatusSummary(String wpId){
    CommuteTypeSummary commuteTypeSummary = workPlaceWorkerDao.findCommuteTypeSummary(wpId);
    long currentWorkerCount = gpsMonitoringDao.findCurrentWorkerCount(wpId);
    return WorkStatusSummaryResponse.builder()
        .commuteSummary(commuteTypeSummary)
        .currentWorkStatus(CurrentWorkStatusSummary.builder()
            .totalWorkerCount(commuteTypeSummary.getTotalWorkerCount())
            .currentWorkerCount(currentWorkerCount)
            .build())
        .build();

  }

  @Deprecated
  public List<AttendantSituationVo> findBleWorkStatusPerCoop(String wpId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("wwpDate", new Date());
    return blsMonitoringService.findAttendantSituationListForMonitoring(condition);
  }

  @Deprecated
  public List<AttendantSituationVo> findGpsWorkStatusPerCoop(String wpId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("wwpDate", new Date());
    return gpsService.findAttendantSituationListForMonitoring(condition);
  }

  @Deprecated
  public List<AttendantVo> findCoopAttendants(String wpId, String coopMbId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("coopMbId", coopMbId);
    condition.put("wwpDate", new Date());
    return blsMonitoringDao.findCoopAttendantsForMonitoring(condition);
  }

  @Deprecated
  public List<AttendantVo> findBleCoopAttendants(String wpId, String coopMbId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("coopMbId", coopMbId);
    condition.put("wwpDate", new Date());
    return blsMonitoringService.findAttendantListForMonitoring(condition);
  }

  @Deprecated
  public List<AttendantVo> findGpsCoopAttendants(String wpId, String coopMbId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("coopMbId", coopMbId);
    condition.put("wwpDate", new Date());
    return gpsService.findAttendantListForMonitoring(condition);
  }


  public OpeEquipmentStatusResponse findOpeEquipmentStatus(String wpId){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    return OpeEquipmentStatusResponse.builder()
        .equipmentList(workEquipmentInfoDao.findOpeEquipmentStatus(condition))
        .build();
  }

  public List<OpeEquipmentVo> findOpeEquipmentListByType(String wpId, int equipmentType){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);
    condition.put("equipmentType", equipmentType);
    return workEquipmentInfoDao.findOpeEquipmentList(condition);
  }

  public List<EnvironmentMeasureDeviceVo> findHazardDeviceList(String wpId){

    List<EnvironmentMeasureDeviceVo> deviceList = new ArrayList<EnvironmentMeasureDeviceVo>();

    Map<Integer, GasSafeRangeDto> safeRangeMap = gasLogDao.findGasSafeRangeDtoList(wpId).stream().collect(
        Collectors.toMap(GasSafeRangeDto::getCategory, Function.identity()));

    List<GasLogDto> gasList = gasLogDao.findRecentGasLog(wpId);
    for(GasLogDto gasDto : gasList ){
      deviceList.add(convertEnvDevice(gasDto, safeRangeMap));
    }
    return deviceList;
  }

  private EnvironmentMeasureDeviceVo convertEnvDevice(GasLogDto gasDto, Map<Integer, GasSafeRangeDto> safeRangeMap){
    EnvironmentMeasureDeviceVo vo = modelMapper.map(gasDto, EnvironmentMeasureDeviceVo.class);
    List<EnvironmentMeasureVo> measureList = new ArrayList<EnvironmentMeasureVo>();
    if(  gasDto.getO2() != null ){
      measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.OXYGEN, gasDto.getO2(), gasDto.getO2Level(), safeRangeMap.get(MeasureEnvironmentType.OXYGEN.getCode()) ));
    }
    if(  gasDto.getH2s() != null ){
      measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.HYDROGEN_SULFIDE, gasDto.getH2s(), gasDto.getH2sLevel(), safeRangeMap.get(MeasureEnvironmentType.HYDROGEN_SULFIDE.getCode())  ));
    }
    if(  gasDto.getCo() != null ){
      measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.CARBON_MONOXIDE, gasDto.getCo(), gasDto.getCoLevel(), safeRangeMap.get(MeasureEnvironmentType.CARBON_MONOXIDE.getCode())  ));
    }
    if(  gasDto.getCh4() != null ){
      measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.METHANE, gasDto.getCh4(), gasDto.getCh4Level(), safeRangeMap.get(MeasureEnvironmentType.METHANE.getCode())  ));
    }
    if(  gasDto.getTemperature() != null ){
      measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.TEMPERATURE, gasDto.getTemperature(), gasDto.getTemperatureLevel(), safeRangeMap.get(MeasureEnvironmentType.TEMPERATURE.getCode())  ));
    }

    if(  gasDto.getHumidity() != null ){
      measureList.add(EnvironmentMeasureVo.build(MeasureEnvironmentType.HUMIDITY, gasDto.getHumidity(), 0, safeRangeMap.get(MeasureEnvironmentType.HUMIDITY.getCode())  ));
    }

    vo.setWpId(gasDto.getWpId());
    vo.setWpName(gasDto.getWpName());
    vo.setHazardPhrase(gasDto.getHazardPhrase());
    vo.setList(measureList);
    return vo;
  }

  public HazardStatusResponse findHazardDeviceStatus(String wpId, int idx){
    List<GasSafeRangeDto> safeRangeList = gasLogDao.findGasSafeRangeDtoList(wpId);
    Map<Integer, GasSafeRangeDto> safeRangeMap = safeRangeList.stream().collect(
        Collectors.toMap(GasSafeRangeDto::getCategory, Function.identity()));
    EnvironmentMeasureDeviceVo dev =  convertEnvDevice(gasLogDao.findRecentGasLogByDevice(idx), safeRangeMap);

    Map<String, Object> conditionMap = new HashMap<String,Object>();
    conditionMap.put("idx", idx);
    conditionMap.put("wpId", wpId);
    //conditionMap.put("startDate", new Date());
    //conditionMap.put("endDate", new Date());
    conditionMap.put("startRow", 0);
    conditionMap.put("pageSize", 600);
    List<GasLogDto> historyList = gasService.findListByCondition(conditionMap)
        .stream()
        .sorted(new Comparator<GasLogDto>() {
      @Override
      public int compare(GasLogDto o1, GasLogDto o2) {
        return o1.getMeasureTime().compareTo(o2.getMeasureTime());
      }
    }).collect(Collectors.toList());

    return HazardStatusResponse.builder()
        .current(dev)
        .historyList(historyList)
        .safeRangeList(safeRangeList)
        .build();
  }

  @Transactional("transactionManager")
  public void updateHazardAlarm(String wpId, int idx, int alarmFlag){
    WorkDeviceInfo deviceInfo = workDeviceInfoRepository.findById(idx).orElse(null);
    if( deviceInfo != null ){
      GasLogRecent recent = gasLogRecentRepository.findById(deviceInfo.getMacAddress()).orElse(null);
      if( recent != null ){
        recent.setDashboardPopup(alarmFlag);
        gasLogRecentRepository.save(recent);
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "디바이스 정보를 찾을 수 없습니다.");
    }

  }



  public List<WorkPlaceCctvDto> findCctvList(String wpId){
    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("status", EnableCode.ENABLED.getCode());
    return workPlaceCctvService.findListByCondition(condition);
  }

  public EntergateMemberStatResponse findGateSystemStat(String wpId){
    EntrGateWorkplaceVo workplaceVo = entrGateWorkplaceDao.findInfo(wpId);
    if( workplaceVo == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "출입시스템 정보를 찾을 수 없습니다.");
    }
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId",wpId);
    condition.put("workInMethods",Collections.EMPTY_LIST);
    EnterGateStatInfo statVo =  entrGateWorkplaceDao.findCurrentGateStat(condition);
    return EntergateMemberStatResponse.builder()
        .system(modelMapper.map(workplaceVo, EnterGateSystemInfo.class))
        .stat(statVo)
        .build();
  }

  public List<AttendantVo> findGateSystemEntranceUserList(String wpId, EntergateWorkerListRequest request){
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);
    return entrGateWorkplaceDao.findGateSystemEntranceUserList(condition);
  }

  public DefaultPageResult<AttendantVo> findGateEntranceHistoryPagingList(String wpId, EntergateWorkerHistoryPagingListRequest request){
    Map<String,Object> condition = request.getConditionMap();
    condition.put("wpId", wpId);
    return DefaultPageResult.<AttendantVo>builder()
        .pageSize(request.getPageSize())
        .currentPage(request.getPage())
        .list(findGateEntranceHistoryList(condition))
        .totalCount(entrGateWorkplaceDao.countGateEntranceHistoryList(condition))
        .build();
  }

  public List<AttendantVo> findGateEntranceHistoryList(Map<String,Object> condition){
    return entrGateWorkplaceDao.findGateEntranceHistoryList(condition);
  }

  @Transactional("transactionManager")
  public void updateAnalyticViewInfo(AnalyticInfoMgrRequest request){
    ImageAnalyticInfoKey key = modelMapper.map(request, ImageAnalyticInfoKey.class);
    ImageAnalyticInfo info = imageAnalyticInfoRepository.findById(key).orElse(null);
    if( info != null ){
      info.setEventView(request.getEventView());
      imageAnalyticInfoRepository.save(info);
    }
  }
}
