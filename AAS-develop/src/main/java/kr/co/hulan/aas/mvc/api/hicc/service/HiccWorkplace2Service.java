package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.DeviceType;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccNationWideZoneResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccSafetyScoreStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccStateIndicatorResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceSafetyScoreStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceState2Response;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccIntegGroupVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccWorkplaceForIntegGroupVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccWorkplaceWeatherForecastVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccStateIndicator2Vo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceDeviceStateContainerVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceDeviceStateVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceEvaluationVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSafeyScoreVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceStateVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceVo;
import kr.co.hulan.aas.mvc.dao.mapper.AdminMsgInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.HiccInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.SafeyHookDao;
import kr.co.hulan.aas.mvc.dao.mapper.WeatherForecastDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkDeviceInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCctvDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceSafeyScoreDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWeatherDao;
import kr.co.hulan.aas.mvc.model.dto.AdminMsgInfoDto;
import kr.co.hulan.aas.mvc.model.dto.HiccInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiccWorkplace2Service {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HiccInfoDao hiccInfoDao;

  @Autowired
  private WorkPlaceDao workPlaceDao;

  @Autowired
  private WorkPlaceSafeyScoreDao workPlaceSafeyScoreDao;

  @Autowired
  private WorkPlaceWeatherDao workPlaceWeatherDao;

  @Autowired
  private WeatherForecastDao weatherForecastDao;

  @Autowired
  private WorkDeviceInfoDao workDeviceInfoDao;

  @Autowired
  private WorkPlaceCctvDao workPlaceCctvDao;

  @Autowired
  private AdminMsgInfoDao adminMsgInfoDao;

  @Autowired
  private SafeyHookDao safeyHookDao;

  public List<HiccWorkplaceSummaryVo> findSupportedWorkplaceByAccount(){
    return workPlaceDao.findSupportedWorkplaceByAccount(AuthenticationHelper.addAdditionalConditionByLevel());
  }

  public HiccNationWideZoneResponse findNationWideWorkplaceInfo(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();

    HiccInfoDto hiccInfo = hiccInfoDao.findHiccInfoByLoginUser(condition);
    if( hiccInfo == null ){
      hiccInfo = hiccInfoDao.findHiccInfo(1); // 기본 정보 가져오기
      if( hiccInfo == null ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "전국현황 표시를 위한 통합관제 기본정보가 존재하지 않습니다.");
      }
    }
    condition.put("hiccNo", hiccInfo.getHiccNo());


    List<HiccIntegGroupVo> integGroupList = hiccInfoDao.HiccIntegGroupStatList(condition);
    Map<Long, HiccIntegGroupVo> map = integGroupList.stream().collect(
        Collectors.toMap(HiccIntegGroupVo::getHrgNo, Function.identity()));
    List<HiccWorkplaceForIntegGroupVo> workplaceList = workPlaceDao.findWorkplaceListForNationWide(condition);
    for( HiccWorkplaceForIntegGroupVo workplaceStat : workplaceList ){
      HiccIntegGroupVo groupVo = map.get(workplaceStat.getHrgNo());
      if( groupVo != null ){
        groupVo.addWorkplace(workplaceStat);
      }
    }
    return HiccNationWideZoneResponse.builder()
        .zoneList(integGroupList)
        .build();
  }

  public HiccStateIndicatorResponse findTotalWorkplaceState(){

    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    HiccInfoDto hiccInfo = hiccInfoDao.findHiccInfoByLoginUser(condition);
    if( hiccInfo == null ){
      hiccInfo = hiccInfoDao.findHiccInfo(1); // 기본 정보 가져오기
      if( hiccInfo == null ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "전국현황 표시를 위한 통합관제 기본정보가 존재하지 않습니다.");
      }
    }
    condition.put("hiccNo", hiccInfo.getHiccNo());

    HiccStateIndicator2Vo indicatorVo = workPlaceDao.findTotalWorkplaceState2(condition);
    if( indicatorVo == null ){
      indicatorVo = new HiccStateIndicator2Vo();
    }

    return HiccStateIndicatorResponse.builder()
        .stateIndicator(indicatorVo)
        .regionStatList(workPlaceDao.findHiccRegionStat(condition))
        .build();
  }

  public HiccWorkplaceState2Response findWorkplaceState(String wpId){
    WorkPlaceDto info = workPlaceDao.findById(wpId);
    if( info == null ){
      throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "존재하지 않는 현장입니다.");
    }
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);

    HiccWorkplaceEvaluationVo evaluationVo = workPlaceSafeyScoreDao.findEvaluationSafeyScore(condition);
    if( evaluationVo != null ){
      evaluationVo.setWorkplaceCount(workPlaceDao.countSupportedWorkplaceByAccount(condition));
    }
    HiccStateIndicator2Vo workplaceState = workPlaceDao.findWorkplaceState2(condition);
    if( workplaceState == null ) {
      workplaceState = new HiccStateIndicator2Vo();
    }

    List<HiccWorkplaceDeviceStateVo> deviceList = new ArrayList<HiccWorkplaceDeviceStateVo>();
    Map<Integer, HiccWorkplaceDeviceStateVo> deviceMap = workDeviceInfoDao.findDeviceStateByWpId(wpId)
        .stream().collect(
            Collectors.toMap(HiccWorkplaceDeviceStateVo::getDeviceType, Function.identity())
        );
    for( DeviceType deviceType :DeviceType.values() ){
      HiccWorkplaceDeviceStateVo vo = deviceMap.get(deviceType.getCode());
      if( vo == null ){
        vo = new HiccWorkplaceDeviceStateVo();
        vo.setDeviceType(deviceType.getCode());
        vo.setDeviceTypeName(deviceType.getName());
        vo.setDeviceCount(0);
      }
      deviceList.add(vo);
    }

    deviceList.add(HiccWorkplaceDeviceStateVo.builder()
        .deviceType(10001)
        .deviceTypeName("안전고리")
        .deviceCount(safeyHookDao.countTodaySafeyHookByWpId(wpId).intValue())
        .build()
    );

    List<HiccWorkplaceDeviceStateVo> workImageDeviceList = new ArrayList<HiccWorkplaceDeviceStateVo>();
    workImageDeviceList.add(HiccWorkplaceDeviceStateVo.builder()
        .deviceType(20001)
        .deviceTypeName("고정식 CCTV")
        .deviceCount(workPlaceCctvDao.countListByCondition(condition).intValue())
        .build()
    );

    return HiccWorkplaceState2Response.builder()
        .info(modelMapper.map(info, HiccWorkplaceVo.class))
        .state(workplaceState)
        .evaluation(evaluationVo)
        .weather(HiccWorkplaceWeatherForecastVo.builder()
            .currentWeather(workPlaceWeatherDao.findHiccWeatherInfo(wpId))
            .forecastWeather(weatherForecastDao.findWeekWeatherForecast(wpId))
            .build())
        .deviceState(HiccWorkplaceDeviceStateContainerVo.builder()
            .deviceList(deviceList)
            .imageDeviceList(workImageDeviceList)
            .build())
        .build();
  }


  public HiccSafetyScoreStateResponse findSafeyScoreState(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    List<HiccWorkplaceSafeyScoreVo> averageList = workPlaceSafeyScoreDao.findAverageSafeyScoreList(condition);
    List<HiccWorkplaceSafeyScoreVo> currentList = workPlaceSafeyScoreDao.findCurrentSafeyScoreList(condition);
    return HiccSafetyScoreStateResponse.builder()
        .list(currentList)
        .averageList(averageList)
        .build();
  }

  public HiccWorkplaceSafetyScoreStateResponse findSafeyScoreStateByWpId(String wpId){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("wpId", wpId);

    HiccWorkplaceEvaluationVo evaluationVo = workPlaceSafeyScoreDao.findEvaluationSafeyScore(condition);
    if( evaluationVo != null ){
      evaluationVo.setWorkplaceCount(workPlaceDao.countSupportedWorkplaceByAccount(condition));
    }
    HiccWorkplaceStateVo workplaceState = workPlaceDao.findWorkplaceState(condition);
    if( workplaceState == null ) {
      workplaceState = new HiccWorkplaceStateVo();
    }
    // TODO averageList

    return HiccWorkplaceSafetyScoreStateResponse.builder()
        .evaluation(evaluationVo)
        .state(workplaceState)
        .list(workPlaceSafeyScoreDao.findSafeyScoreListByWpId(condition))
        .averageList(workPlaceSafeyScoreDao.findSafeyScoreListByWpId(condition))
        .build();
  }


  public List<AdminMsgInfoDto> findRecentAdminMsgList(List<Integer> alarmGrades){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    if( alarmGrades != null && alarmGrades.size() != 0 ){
      condition.put("alarmGrades", alarmGrades);
    }
    condition.put("startRow", 0);
    condition.put("pageSize", 50);
    return adminMsgInfoDao.findRecentList(condition);
  }

}
