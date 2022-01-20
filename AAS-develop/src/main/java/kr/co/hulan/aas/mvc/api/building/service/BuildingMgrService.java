package kr.co.hulan.aas.mvc.api.building.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.BuildingAreaType;
import kr.co.hulan.aas.common.code.BuildingFloorType;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.building.controller.request.*;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListBuildingFloorResponse;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListBuildingResponse;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import kr.co.hulan.aas.mvc.dao.mapper.BuildingDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.dao.repository.BuildingFloorRepository;
import kr.co.hulan.aas.mvc.dao.repository.BuildingRepository;
import kr.co.hulan.aas.mvc.dao.repository.SensorBuildingLocationRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.Building;
import kr.co.hulan.aas.mvc.model.domain.BuildingFloor;
import kr.co.hulan.aas.mvc.model.domain.BuildingFloorCompositeKey;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import kr.co.hulan.aas.mvc.model.dto.BuildingFloorDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BuildingMgrService {

  private Logger logger = LoggerFactory.getLogger(BuildingMgrService.class);

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private BuildingDao buildingDao;

  @Autowired
  private BuildingRepository buildingRepository;

  @Autowired
  private BuildingFloorRepository buildingFloorRepository;

  @Autowired
  private SensorBuildingLocationRepository sensorBuildingLocationRepository;

  @Autowired
  private WorkPlaceDao workPlaceDao;

  @Autowired
  private WorkPlaceRepository workPlaceRepository;

  @Autowired
  FileService fileService;

  public ListBuildingResponse findListPage(ListBuildingRequest request) {
    return new ListBuildingResponse(
        findListCountByCondition(request.getConditionMap()),
        findListByCondition(request.getConditionMap())
    );
  }


  public List<BuildingDto> findListByCondition(Map<String, Object> conditionMap) {
    return buildingDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long findListCountByCondition(Map<String, Object> conditionMap) {
    return buildingDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public BuildingDto findById(long buildingNo) {
    BuildingDto buildingDto = buildingDao.findById(buildingNo);
    if (buildingDto != null) {
      return buildingDto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }


  @Transactional("transactionManager")
  public long create(CreateBuildingRequest request){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    Optional<WorkPlace> workPlaceOp = workPlaceRepository.findById(request.getWpId());
    if( !workPlaceOp.isPresent()){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
    }

    request.init();

    BuildingAreaType areaType = BuildingAreaType.get(request.getAreaType());
    if( areaType == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 지역타입입니다.["+request.getAreaType()+"]");
    }

    MemberLevel level = MemberLevel.get(loginUser.getMbLevel());
    if ( level == MemberLevel.FIELD_MANAGER ) {
      if( !StringUtils.equals(loginUser.getWpId(), request.getWpId() )){
        logger.warn(this.getClass().getSimpleName()+"|NOT_EQUAL_WP_ID|"+loginUser.getWpId()+"|"+request.getWpId());
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
      }
    }
    else if( level != MemberLevel.SUPER_ADMIN ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
    }


    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    Building saveDto = modelMapper.map(request, Building.class);
    saveDto.setCreateDate(new Date());
    saveDto.setCreator( loginUser.getUsername() );
    saveDto.setUpdateDate(new Date());
    saveDto.setUpdater( loginUser.getUsername() );
    buildingRepository.save(saveDto);

    boolean existsUploadedFile = request.getViewFloorFile() != null
            && StringUtils.isNotEmpty(request.getViewFloorFile().getFileName())
            && StringUtils.isNotEmpty(request.getViewFloorFile().getFileOriginalName());
    if( existsUploadedFile ){
      UploadedFile viewMapFile = request.getViewFloorFile();
      fileService.copyTempFile(viewMapFile.getFileName(), fileService.getBuildingFilePath(saveDto.getBuildingNo(), true));
      saveDto.setViewFloorFileLocation(Storage.LOCAL_STORAGE.getCode());
      saveDto.setViewFloorFileName(viewMapFile.getFileName());
      saveDto.setViewFloorFileNameOrg(viewMapFile.getFileOriginalName());
      saveDto.setViewFloorFilePath(fileService.getBuildingFilePath(saveDto.getBuildingNo(), false));

      boolean existsCrossSectionUploadedFile = request.getCrossSectionFile() != null
              && StringUtils.isNotEmpty(request.getCrossSectionFile().getFileName())
              && StringUtils.isNotEmpty(request.getCrossSectionFile().getFileOriginalName());
      if( existsCrossSectionUploadedFile ){
        UploadedFile crossSectionFile = request.getCrossSectionFile();
        fileService.copyTempFile(crossSectionFile.getFileName(), fileService.getBuildingFilePath(saveDto.getBuildingNo(), true));
        saveDto.setCrossSectionFileLocation(Storage.LOCAL_STORAGE.getCode());
        saveDto.setCrossSectionFileName(crossSectionFile.getFileName());
        saveDto.setCrossSectionFileNameOrg(crossSectionFile.getFileOriginalName());
        saveDto.setCrossSectionFilePath(fileService.getBuildingFilePath(saveDto.getBuildingNo(), false));
      }

      buildingRepository.save(saveDto);

      List<BuildingFloor> floorList = new ArrayList<BuildingFloor>();
      if( areaType == BuildingAreaType.BUILDING ){
        for( int idx = saveDto.getFloorDownstair() ; idx <= saveDto.getFloorUpstair() ; idx++ ){
          if( idx != 0 ){
            BuildingFloor floor = new BuildingFloor();
            floor.setBuildingNo(saveDto.getBuildingNo());
            floor.setFloor(idx);
            if( idx > 0 ){
              floor.setFloorName("지상 "+idx+" 층");
            }
            else {
              floor.setFloorName("지하 "+(-idx)+" 층");
            }
            floor.setFloorType(BuildingFloorType.FLOOR.getCode());
            floor.setCrossSectionGridX(0);
            floor.setCrossSectionGridY(0);
            floor.setUpdateDate(new Date());
            floor.setUpdater( loginUser.getUsername() );
            floorList.add(floor);
          }
        }

        if( saveDto.getContainRoof() == 1 ){
          BuildingFloor floor = new BuildingFloor();
          floor.setBuildingNo(saveDto.getBuildingNo());
          floor.setFloor(BuildingFloorType.ROOF.getCode());
          floor.setFloorType(BuildingFloorType.ROOF.getCode());
          floor.setFloorName(BuildingFloorType.ROOF.getName());
          floor.setCrossSectionGridX(0);
          floor.setCrossSectionGridY(0);
          floor.setUpdateDate(new Date());
          floor.setUpdater( loginUser.getUsername() );
          floorList.add(floor);
        }
        if( saveDto.getContainGangform() == 1 ){
          BuildingFloor floor = new BuildingFloor();
          floor.setBuildingNo(saveDto.getBuildingNo());
          floor.setFloor(BuildingFloorType.GANAFORM.getCode());
          floor.setFloorType(BuildingFloorType.GANAFORM.getCode());
          floor.setFloorName(BuildingFloorType.GANAFORM.getName());
          floor.setCrossSectionGridX(0);
          floor.setCrossSectionGridY(0);
          floor.setUpdateDate(new Date());
          floor.setUpdater( loginUser.getUsername() );
          floorList.add(floor);
        }
      }
      else if( areaType == BuildingAreaType.DISTRICT_GROUP ){
        BuildingFloor floor = new BuildingFloor();
        floor.setBuildingNo(saveDto.getBuildingNo());
        floor.setFloor(1);
        floor.setFloorType(BuildingFloorType.DISTRICT_GROUP.getCode());
        floor.setFloorName(saveDto.getBuildingName());
        floor.setCrossSectionGridX(0);
        floor.setCrossSectionGridY(0);
        floor.setUpdateDate(new Date());
        floor.setUpdater( loginUser.getUsername() );
        floorList.add(floor);
      }
      else {
        BuildingFloor floor = new BuildingFloor();
        floor.setBuildingNo(saveDto.getBuildingNo());
        floor.setFloor(BuildingFloorType.FLOOR.getCode());
        floor.setFloorType(BuildingFloorType.FLOOR.getCode());
        floor.setFloorName(saveDto.getBuildingName());
        floor.setCrossSectionGridX(0);
        floor.setCrossSectionGridY(0);
        floor.setUpdateDate(new Date());
        floor.setUpdater( loginUser.getUsername() );
        floorList.add(floor);
      }

      if( floorList.size() > 0 ){
        buildingFloorRepository.saveAll(floorList);
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "층 도면은 필수 입니다.");
    }

    return saveDto.getBuildingNo();
  }


  @Transactional("transactionManager")
  public void update(UpdateBuildingRequest request, long  buildingNo ){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if ( request.getBuildingNo() != buildingNo ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
    }

    request.init();

    BuildingAreaType areaType = BuildingAreaType.get(request.getAreaType());
    if( areaType == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 지역타입 입니다.["+request.getAreaType()+"]");
    }

    Optional<Building>  buildingOp = buildingRepository.findById(buildingNo);
    if( buildingOp.isPresent() ){
      Building currentInfo = buildingOp.get();

      MemberLevel level = MemberLevel.get(loginUser.getMbLevel());
      if ( level == MemberLevel.FIELD_MANAGER ) {
        if( !StringUtils.equals(loginUser.getWpId(), currentInfo.getWpId() )){
          logger.warn(this.getClass().getSimpleName()+"|NOT_EQUAL_WP_ID|["+loginUser.getWpId()+"]|["+currentInfo.getWpId()+"]");
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
        }
      }
      else if( level != MemberLevel.SUPER_ADMIN ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
      }

      ModelMapper modelMapper = new ModelMapper();
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      modelMapper.map(request, currentInfo);

      currentInfo.setUpdateDate(new Date());
      currentInfo.setUpdater( loginUser.getUsername() );

      boolean existsUploadedFile = request.getViewFloorFile() != null
              && StringUtils.isNotEmpty(request.getViewFloorFile().getFileName())
              && StringUtils.isNotEmpty(request.getViewFloorFile().getFileOriginalName());
      if( existsUploadedFile ){
        UploadedFile viewMapFile = request.getViewFloorFile();
        if( !StringUtils.equals( viewMapFile.getFileName(), currentInfo.getViewFloorFileName() )){
          fileService.moveTempFile(viewMapFile.getFileName(), fileService.getBuildingFilePath(currentInfo.getBuildingNo(), true));
          currentInfo.setViewFloorFileLocation(Storage.LOCAL_STORAGE.getCode());
          currentInfo.setViewFloorFileName(viewMapFile.getFileName());
          currentInfo.setViewFloorFileNameOrg(viewMapFile.getFileOriginalName());
          currentInfo.setViewFloorFilePath(fileService.getBuildingFilePath(currentInfo.getBuildingNo(), false));
        }
      }
      else {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "층 도면은 필수 입니다.");
      }

      boolean existsCrossSectionUploadedFile = request.getCrossSectionFile() != null
              && StringUtils.isNotEmpty(request.getCrossSectionFile().getFileName())
              && StringUtils.isNotEmpty(request.getCrossSectionFile().getFileOriginalName());
      if( existsCrossSectionUploadedFile ){
        UploadedFile crossSectionFile = request.getCrossSectionFile();
        boolean existsBeforeCrossSecionFile = StringUtils.isNotEmpty(currentInfo.getCrossSectionFileName())
                && StringUtils.isNotEmpty(currentInfo.getCrossSectionFileNameOrg())
                && currentInfo.getCrossSectionFileLocation() != null;
        if( existsBeforeCrossSecionFile ){
          if( !StringUtils.equals( crossSectionFile.getFileName(), currentInfo.getCrossSectionFileName() )){
            fileService.moveTempFile(crossSectionFile.getFileName(), fileService.getBuildingFilePath(currentInfo.getBuildingNo(), true));
            currentInfo.setCrossSectionFileLocation(Storage.LOCAL_STORAGE.getCode());
            currentInfo.setCrossSectionFileName(crossSectionFile.getFileName());
            currentInfo.setCrossSectionFileNameOrg(crossSectionFile.getFileOriginalName());
            currentInfo.setCrossSectionFilePath(fileService.getBuildingFilePath(currentInfo.getBuildingNo(), false));
          }
        }
        else {
          fileService.moveTempFile(crossSectionFile.getFileName(), fileService.getBuildingFilePath(currentInfo.getBuildingNo(), true));
          currentInfo.setCrossSectionFileLocation(Storage.LOCAL_STORAGE.getCode());
          currentInfo.setCrossSectionFileName(crossSectionFile.getFileName());
          currentInfo.setCrossSectionFileNameOrg(crossSectionFile.getFileOriginalName());
          currentInfo.setCrossSectionFilePath(fileService.getBuildingFilePath(currentInfo.getBuildingNo(), false));
        }
      }
      else {
        currentInfo.setCrossSectionFileLocation(null);
        currentInfo.setCrossSectionFileName(null);
        currentInfo.setCrossSectionFileNameOrg(null);
        currentInfo.setCrossSectionFilePath(null);
      }
      buildingRepository.save(currentInfo);

      Map<Integer, BuildingFloor> currentFloorMap = buildingFloorRepository.findAllByBuildingNo(currentInfo.getBuildingNo())
              .stream()
              .collect( Collectors.toMap(BuildingFloor::getFloor,  Function.identity()) );

      List<BuildingFloor> floorList = new ArrayList<BuildingFloor>();
      List<BuildingFloor> deleteFloorList = new ArrayList<BuildingFloor>();

      if( areaType == BuildingAreaType.BUILDING ){
        for( int idx = currentInfo.getFloorDownstair() ; idx <= currentInfo.getFloorUpstair() ; idx++ ){
          if( idx != 0 ){
            BuildingFloor existsFloor = currentFloorMap.remove(idx);
            if( existsFloor == null ){
              BuildingFloor floor = new BuildingFloor();
              floor.setBuildingNo(currentInfo.getBuildingNo());
              floor.setFloor(idx);
              if( idx > 0 ){
                floor.setFloorName("지상 "+idx+" 층");
              }
              else {
                floor.setFloorName("지하 "+(-idx)+" 층");
              }
              floor.setFloorType(BuildingFloorType.FLOOR.getCode());
              floor.setCrossSectionGridX(0);
              floor.setCrossSectionGridY(0);
              floor.setUpdateDate(new Date());
              floor.setUpdater( loginUser.getUsername() );
              floorList.add(floor);
            }
            else {
              if( idx > 0 ){
                existsFloor.setFloorName("지상 "+idx+" 층");
              }
              else {
                existsFloor.setFloorName("지하 "+(-idx)+" 층");
              }
              existsFloor.setFloorType(BuildingFloorType.FLOOR.getCode());
              existsFloor.setUpdateDate(new Date());
              existsFloor.setUpdater( loginUser.getUsername() );
              floorList.add(existsFloor);
            }
          }
        }

        BuildingFloor roofFloor = currentFloorMap.remove(BuildingFloorType.ROOF.getCode());
        if( currentInfo.getContainRoof() == 1 ){
          if( roofFloor == null ){
            BuildingFloor floor = new BuildingFloor();
            floor.setBuildingNo(currentInfo.getBuildingNo());
            floor.setFloor(BuildingFloorType.ROOF.getCode());
            floor.setFloorType(BuildingFloorType.ROOF.getCode());
            floor.setFloorName(BuildingFloorType.ROOF.getName());
            floor.setCrossSectionGridX(0);
            floor.setCrossSectionGridY(0);
            floor.setUpdateDate(new Date());
            floor.setUpdater( loginUser.getUsername() );
            floorList.add(floor);
          }
        }
        else if( roofFloor != null ){
          deleteFloorList.add(roofFloor);
        }

        BuildingFloor gangFormFoor = currentFloorMap.remove(BuildingFloorType.GANAFORM.getCode());
        if( currentInfo.getContainGangform() == 1 ){
          if( gangFormFoor == null ){
            BuildingFloor floor = new BuildingFloor();
            floor.setBuildingNo(currentInfo.getBuildingNo());
            floor.setFloor(BuildingFloorType.GANAFORM.getCode());
            floor.setFloorType(BuildingFloorType.GANAFORM.getCode());
            floor.setFloorName(BuildingFloorType.GANAFORM.getName());
            floor.setCrossSectionGridX(0);
            floor.setCrossSectionGridY(0);
            floor.setUpdateDate(new Date());
            floor.setUpdater( loginUser.getUsername() );
            floorList.add(floor);
          }
        }
        else if( gangFormFoor != null ){
          deleteFloorList.add(gangFormFoor);
        }
        currentFloorMap.values().forEach( floor -> deleteFloorList.add(floor) );
      }
      else if( areaType == BuildingAreaType.DISTRICT_GROUP ){
        Collection<BuildingFloor> beforeFloorList = currentFloorMap.values();
        for( BuildingFloor floor : beforeFloorList ){
          floor.setFloorType(BuildingFloorType.DISTRICT_GROUP.getCode());
          floor.setUpdateDate(new Date());
          floor.setUpdater( loginUser.getUsername() );
          floorList.add(floor);
        }
      }
      else {
        BuildingFloor defaultFloor = currentFloorMap.remove(BuildingFloorType.FLOOR.getCode());
        if( defaultFloor == null ){
          BuildingFloor floor = new BuildingFloor();
          floor.setBuildingNo(currentInfo.getBuildingNo());
          floor.setFloor(BuildingFloorType.FLOOR.getCode());
          floor.setFloorType(BuildingFloorType.FLOOR.getCode());
          floor.setFloorName(currentInfo.getBuildingName());
          floor.setCrossSectionGridX(0);
          floor.setCrossSectionGridY(0);
          floor.setUpdateDate(new Date());
          floor.setUpdater( loginUser.getUsername() );
          floorList.add(floor);
        }
        else {
          defaultFloor.setFloorType(BuildingFloorType.FLOOR.getCode());
          defaultFloor.setFloorName(currentInfo.getBuildingName());
          defaultFloor.setUpdateDate(new Date());
          defaultFloor.setUpdater( loginUser.getUsername() );
          floorList.add(defaultFloor);
        }
        currentFloorMap.values().forEach( floor -> deleteFloorList.add(floor) );
      }

      if( deleteFloorList.size() > 0 ){
        buildingFloorRepository.deleteAll(deleteFloorList);
        sensorBuildingLocationRepository.deleteInvalidFloorSensor(currentInfo.getBuildingNo(), deleteFloorList.stream().map(floor -> floor.getFloor()).collect(Collectors.toList()));
      }

      if( floorList.size() > 0 ){
        buildingFloorRepository.saveAll(floorList);
      }
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 빌딩입니다.");
    }
  }

  @Transactional("transactionManager")
  public void delete(long buildingNo){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }
    Optional<Building>  buildingOp = buildingRepository.findById(buildingNo);
    if( buildingOp.isPresent() ){
      Building deletedBuilding = buildingOp.get();

      MemberLevel level = MemberLevel.get(loginUser.getMbLevel());
      if ( level == MemberLevel.FIELD_MANAGER ) {
        if( !StringUtils.equals(loginUser.getWpId(), deletedBuilding.getWpId() )){
          logger.warn(this.getClass().getSimpleName()+"|NOT_EQUAL_WP_ID|"+loginUser.getWpId()+"|"+deletedBuilding.getWpId());
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
        }
      }
      else if( level != MemberLevel.SUPER_ADMIN ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
      }
      buildingRepository.deleteById(deletedBuilding.getBuildingNo());
    }
  }

  @Transactional("transactionManager")
  public void deleteMultiple(List<Long> buildingNos) {
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }
    for (Long buildingNo : buildingNos) {
      buildingRepository.deleteById(buildingNo);
    }
  }


  public ListBuildingFloorResponse findFloorListPage(ListBuildingFloorRequest request) {
    return new ListBuildingFloorResponse(
            findFloorListCountByCondition(request.getConditionMap()),
            findFloorListByCondition(request.getConditionMap())
    );
  }


  public List<BuildingFloorDto> findFloorListByCondition(Map<String, Object> conditionMap) {
    return buildingDao.findFloorListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  private Long findFloorListCountByCondition(Map<String, Object> conditionMap) {
    return buildingDao.findFloorListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
  }

  public List<BuildingFloorDto>  findFloorListByBuildingNo(long buildingNo) {
    Map<String, Object> conditionMap = new HashMap<String, Object>();
    conditionMap.put("buildingNo",buildingNo);
    return buildingDao.findFloorListByCondition(conditionMap);
  }

  public BuildingFloorDto findFloor(long buildingNo, int floor) {
    BuildingFloorDto dto = new BuildingFloorDto();
    dto.setBuildingNo(buildingNo);
    dto.setFloor(floor);
    BuildingFloorDto buildingFloorDto = buildingDao.findFloor(dto);
    if (buildingFloorDto != null) {
      return buildingFloorDto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void createFloor(long buildingNo, CreateBuildingFloorRequest request){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    Building  buildingOp = buildingRepository.findById(buildingNo).orElse(null);
    if( buildingOp == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "빌딩정보를 찾을 수 없습니다.");
    }

    MemberLevel level = MemberLevel.get(loginUser.getMbLevel());
    if ( level == MemberLevel.FIELD_MANAGER ) {
      if( !StringUtils.equals(loginUser.getWpId(), buildingOp.getWpId() )){
        logger.warn(this.getClass().getSimpleName()+"|NOT_EQUAL_WP_ID|"+loginUser.getWpId()+"|"+buildingOp.getWpId());
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
      }
    }
    else if( level != MemberLevel.SUPER_ADMIN ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
    }

    BuildingAreaType areaType = BuildingAreaType.get(buildingOp.getAreaType());
    if( areaType != BuildingAreaType.DISTRICT_GROUP){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현재는 빌딩타입이 구획인 경우만 등록할 수 있습니다.");
    }

    int newFloorNo = 1;
    BuildingFloor maxFloor = buildingFloorRepository.findTopByBuildingNoOrderByFloorDesc(buildingNo);
    if( maxFloor != null ){
      newFloorNo = maxFloor.getFloor() + 1;
    }

    BuildingFloor floor = modelMapper.map(request, BuildingFloor.class);
    floor.setBuildingNo(buildingNo);
    floor.setFloor(newFloorNo);
    floor.setFloorType(BuildingFloorType.DISTRICT_GROUP.getCode());
    floor.setUpdateDate(new Date());
    floor.setUpdater(loginUser.getMbId());

    boolean existsUploadedFile = request.getViewFloorFile() != null
        && StringUtils.isNotEmpty(request.getViewFloorFile().getFileName())
        && StringUtils.isNotEmpty(request.getViewFloorFile().getFileOriginalName());
    if( existsUploadedFile ){
      UploadedFile viewMapFile = request.getViewFloorFile();
      fileService.moveTempFile(viewMapFile.getFileName(), fileService.getBuildingFilePath(buildingNo, true));
      floor.setViewFloorFileLocation(Storage.LOCAL_STORAGE.getCode());
      floor.setViewFloorFileName(viewMapFile.getFileName());
      floor.setViewFloorFileNameOrg(viewMapFile.getFileOriginalName());
      floor.setViewFloorFilePath(fileService.getBuildingFilePath(buildingNo, false));
    }
    else {
      floor.setViewFloorFileLocation(null);
      floor.setViewFloorFileName(null);
      floor.setViewFloorFileNameOrg(null);
      floor.setViewFloorFilePath(null);
    }
    buildingFloorRepository.save(floor);
  }

  @Transactional("transactionManager")
  public void updateFloor(UpdateBuildingFloorRequest request, long buildingNo, int floor){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    Building  buildingOp = buildingRepository.findById(buildingNo).orElse(null);
    if( buildingOp == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "빌딩정보를 찾을 수 없습니다.");
    }

    MemberLevel level = MemberLevel.get(loginUser.getMbLevel());
    if ( level == MemberLevel.FIELD_MANAGER ) {
      if( !StringUtils.equals(loginUser.getWpId(), buildingOp.getWpId() )){
        logger.warn(this.getClass().getSimpleName()+"|NOT_EQUAL_WP_ID|"+loginUser.getWpId()+"|"+buildingOp.getWpId());
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
      }
    }
    else if( level != MemberLevel.SUPER_ADMIN ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
    }

    BuildingFloorCompositeKey key = new BuildingFloorCompositeKey();
    key.setBuildingNo(buildingNo);
    key.setFloor(floor);
    Optional<BuildingFloor>  buildingFloorOp = buildingFloorRepository.findById(key);
    if( buildingFloorOp.isPresent() ){
      BuildingFloor currentInfo = buildingFloorOp.get();

      ModelMapper modelMapper = new ModelMapper();
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      modelMapper.map(request, currentInfo);

      currentInfo.setUpdateDate(new Date());
      currentInfo.setUpdater( loginUser.getUsername() );

      boolean existsUploadedFile = request.getViewFloorFile() != null
              && StringUtils.isNotEmpty(request.getViewFloorFile().getFileName())
              && StringUtils.isNotEmpty(request.getViewFloorFile().getFileOriginalName());
      if( existsUploadedFile ){
        UploadedFile viewMapFile = request.getViewFloorFile();
        if( !StringUtils.equals( viewMapFile.getFileName(), currentInfo.getViewFloorFileName() )){
          fileService.moveTempFile(viewMapFile.getFileName(), fileService.getBuildingFilePath(currentInfo.getBuildingNo(), true));
          currentInfo.setViewFloorFileLocation(Storage.LOCAL_STORAGE.getCode());
          currentInfo.setViewFloorFileName(viewMapFile.getFileName());
          currentInfo.setViewFloorFileNameOrg(viewMapFile.getFileOriginalName());
          currentInfo.setViewFloorFilePath(fileService.getBuildingFilePath(currentInfo.getBuildingNo(), false));
        }
      }
      else {
        currentInfo.setViewFloorFileLocation(null);
        currentInfo.setViewFloorFileName(null);
        currentInfo.setViewFloorFileNameOrg(null);
        currentInfo.setViewFloorFilePath(null);
      }
      buildingFloorRepository.save(currentInfo);
    }
    else {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 층 입니다.");
    }
  }

  @Transactional("transactionManager")
  public void deleteFloor(long buildingNo, int floor){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser(true);

    Building  buildingOp = buildingRepository.findById(buildingNo).orElse(null);
    if( buildingOp == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "빌딩정보를 찾을 수 없습니다.");
    }

    MemberLevel level = MemberLevel.get(loginUser.getMbLevel());
    if ( level == MemberLevel.FIELD_MANAGER ) {
      if( !StringUtils.equals(loginUser.getWpId(), buildingOp.getWpId() )){
        logger.warn(this.getClass().getSimpleName()+"|NOT_EQUAL_WP_ID|"+loginUser.getWpId()+"|"+buildingOp.getWpId());
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
      }
    }
    else if( level != MemberLevel.SUPER_ADMIN ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "권한이 없는 현장입니다.");
    }

    BuildingAreaType areaType = BuildingAreaType.get(buildingOp.getAreaType());
    if( areaType != BuildingAreaType.DISTRICT_GROUP){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "현재는 빌딩타입이 구획인 경우만 삭제할 수 있습니다.");
    }

    BuildingFloorCompositeKey key = new BuildingFloorCompositeKey();
    key.setBuildingNo(buildingNo);
    key.setFloor(floor);
    Optional<BuildingFloor>  buildingFloorOp = buildingFloorRepository.findById(key);
    if( buildingFloorOp.isPresent() ){
      buildingFloorRepository.delete(buildingFloorOp.get());
    }
  }
}
