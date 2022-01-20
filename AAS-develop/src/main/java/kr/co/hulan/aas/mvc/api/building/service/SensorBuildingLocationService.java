package kr.co.hulan.aas.mvc.api.building.service;

import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.building.controller.request.*;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListSensorBuildingLocationByFloorResponse;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListSensorBuildingLocationResponse;
import kr.co.hulan.aas.mvc.api.building.controller.response.ListSensorBuildingSituationResponse;
import kr.co.hulan.aas.mvc.api.building.dto.SensorBuildingSituationDto;
import kr.co.hulan.aas.mvc.dao.mapper.BuildingDao;
import kr.co.hulan.aas.mvc.dao.mapper.SensorBuildingLocationDao;
import kr.co.hulan.aas.mvc.dao.repository.BuildingFloorRepository;
import kr.co.hulan.aas.mvc.dao.repository.SensorBuildingLocationRepository;
import kr.co.hulan.aas.mvc.model.domain.Building;
import kr.co.hulan.aas.mvc.model.domain.BuildingFloor;
import kr.co.hulan.aas.mvc.model.domain.BuildingFloorCompositeKey;
import kr.co.hulan.aas.mvc.model.domain.SensorBuildingLocation;
import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import kr.co.hulan.aas.mvc.model.dto.BuildingFloorDto;
import kr.co.hulan.aas.mvc.model.dto.SensorBuildingLocationDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SensorBuildingLocationService {

  @Autowired
  private BuildingDao buildingDao;

  @Autowired
  private SensorBuildingLocationRepository sensorBuildingLocationRepository;

  @Autowired
  private BuildingFloorRepository buildingFloorRepository;

  @Autowired
  private SensorBuildingLocationDao sensorBuildingLocationDao;

  public ListSensorBuildingLocationByFloorResponse findListByFloor(ListSensorBuildingLocationByFloorRequest request) {
    BuildingDto dto = buildingDao.findById(request.getBuildingNo());
    if( dto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 건물입니다.");
    }
    BuildingFloorDto key = new BuildingFloorDto();
    key.setBuildingNo(request.getBuildingNo());
    key.setFloor(request.getFloor());
    BuildingFloorDto buildingFloorDto = buildingDao.findFloor(key);
    if( buildingFloorDto == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 건물 층입니다.");
    }
    return ListSensorBuildingLocationByFloorResponse.builder()
            .buildingNo(buildingFloorDto.getBuildingNo())
            .floor(buildingFloorDto.getFloor())
            .viewFloorFileName(StringUtils.defaultIfEmpty( buildingFloorDto.getViewFloorFileName(), dto.getViewFloorFileName()))
            .viewFloorFileNameOrg(StringUtils.defaultIfEmpty( buildingFloorDto.getViewFloorFileNameOrg(), dto.getViewFloorFileNameOrg()))
            .viewFloorFilePath(StringUtils.defaultIfEmpty( buildingFloorDto.getViewFloorFilePath(), dto.getViewFloorFilePath()))
            .viewFloorFileLocation( StringUtils.isNotEmpty( buildingFloorDto.getViewFloorFileName() ) ? buildingFloorDto.getViewFloorFileLocation()  : dto.getViewFloorFileLocation())
            .sensorList( sensorBuildingLocationDao.findListByFloor(request.getConditionMap()) )
            .build();
  }


  public ListSensorBuildingLocationResponse findListPage(ListSensorBuildingLocationRequest request) {
    return new ListSensorBuildingLocationResponse(
        findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
        findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
    );
  }


  public List<SensorBuildingLocationDto> findListByCondition(Map<String, Object> conditionMap) {
    return sensorBuildingLocationDao.findListByCondition(conditionMap);
  }

  private Long findListCountByCondition(Map<String, Object> conditionMap) {
    return sensorBuildingLocationDao.findListCountByCondition(conditionMap);
  }

  public ListSensorBuildingSituationResponse findSituationListPage(ListSensorBuildingSituationRequest request) {
    return new ListSensorBuildingSituationResponse(
        findSituationListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
        findSituationListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
    );
  }

  public List<SensorBuildingSituationDto> findSituationListByCondition(Map<String, Object> conditionMap) {
    return sensorBuildingLocationDao.findSituationListByCondition(conditionMap);
  }

  private Long findSituationListCountByCondition(Map<String, Object> conditionMap) {
    return sensorBuildingLocationDao.findSituationListCountByCondition(conditionMap);
  }


  public SensorBuildingLocationDto findById(int siIdx) {
    SensorBuildingLocationDto sensorBuildingLocationDto = sensorBuildingLocationDao.findById(siIdx);
    if (sensorBuildingLocationDto != null) {
      return sensorBuildingLocationDto;
    } else {
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
    }
  }

  @Transactional("transactionManager")
  public void update(UpdateSensorBuildingLocationRequest request, int siIdx ){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    if ( request.getSiIdx() != siIdx ) {
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
    }

    BuildingFloorCompositeKey changedFloor = BuildingFloorCompositeKey.builder()
        .buildingNo(request.getBuildingNo())
        .floor(request.getFloor())
        .build();

    BuildingFloor buildinFloor = buildingFloorRepository.findById(changedFloor).orElse(null);
    if( buildinFloor == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 층 정보를 찾을 수 없습니다.");
    }
    else if( EnableCode.get( buildinFloor.getActivated()) != EnableCode.ENABLED ){
      buildinFloor.setActivated(EnableCode.ENABLED.getCode());
      buildingFloorRepository.save(buildinFloor);
    }

    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    Optional<SensorBuildingLocation> sensorBuildingLocationOp = sensorBuildingLocationRepository.findById(siIdx);
    if( sensorBuildingLocationOp.isPresent() ){
      SensorBuildingLocation currentInfo = sensorBuildingLocationOp.get();
      BuildingFloorCompositeKey currentFloorKey = BuildingFloorCompositeKey.builder()
          .buildingNo(currentInfo.getBuildingNo())
          .floor(currentInfo.getFloor())
          .build();

      modelMapper.map(request, currentInfo);

      currentInfo.setUpdateDate(new Date());
      currentInfo.setUpdater( loginUser.getUsername() );

      sensorBuildingLocationRepository.save(currentInfo);

      /*
      if( !currentFloorKey.equals(buildinFloor)){
        long totalSensorCount = sensorBuildingLocationRepository.countByBuildingNoAndFloor(
            currentFloorKey.getBuildingNo(),
            currentFloorKey.getFloor()
        );

        if( totalSensorCount == 0 ){
          BuildingFloor currentFloor = buildingFloorRepository.findById(currentFloorKey).orElse(null);
          if( currentFloor != null ){
            if( EnableCode.get( currentFloor.getActivated()) != EnableCode.DISABLED ){
              currentFloor.setActivated(EnableCode.DISABLED.getCode());
              buildingFloorRepository.save(currentFloor);
            }
          }
        }
      }
       */
    }
    else {
      SensorBuildingLocation currentInfo = modelMapper.map(request, SensorBuildingLocation.class);
      currentInfo.setCreateDate(new Date());
      currentInfo.setCreator( loginUser.getUsername() );
      currentInfo.setUpdateDate(new Date());
      currentInfo.setUpdater( loginUser.getUsername() );

      sensorBuildingLocationRepository.save(currentInfo);
    }
  }

  @Transactional("transactionManager")
  public void delete(int siIdx){
    SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
    if (loginUser == null) {
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }
    Optional<SensorBuildingLocation> sensorBuildingLocationOp = sensorBuildingLocationRepository.findById(siIdx);
    if( sensorBuildingLocationOp.isPresent() ){
      SensorBuildingLocation sensorBuildingLocation = sensorBuildingLocationOp.get();
      BuildingFloorCompositeKey floorKey = BuildingFloorCompositeKey.builder()
          .buildingNo(sensorBuildingLocation.getBuildingNo())
          .floor(sensorBuildingLocation.getFloor())
          .build();

      sensorBuildingLocationRepository.delete(sensorBuildingLocation);

      /*
      long totalSensorCount = sensorBuildingLocationRepository.countByBuildingNoAndFloor(
          floorKey.getBuildingNo(),
          floorKey.getFloor()
      );

      if( totalSensorCount == 0 ){
        BuildingFloor buildinFloor = buildingFloorRepository.findById(floorKey).orElse(null);
        if( buildinFloor != null ){
          if( EnableCode.get( buildinFloor.getActivated()) != EnableCode.DISABLED ){
            buildinFloor.setActivated(EnableCode.DISABLED.getCode());
            buildingFloorRepository.save(buildinFloor);
          }
        }
      }
       */
    }
  }
}
