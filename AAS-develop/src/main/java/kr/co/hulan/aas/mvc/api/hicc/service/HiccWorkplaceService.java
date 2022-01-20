package kr.co.hulan.aas.mvc.api.hicc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccNationWideInfoResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccSafetyScoreStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceSafetyScoreStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.controller.response.HiccWorkplaceStateResponse;
import kr.co.hulan.aas.mvc.api.hicc.vo.AttendanceDayStatVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.AttendanceWeekHistoryVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccIntegGroupVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccNationWideStateIndicatorVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccNationWideWorkplaceAllVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccWorkplaceForIntegGroupVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccStateIndicatorVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceEvaluationVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSafeyScoreVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceStateVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSidoVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryPerSigunguVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceSummaryVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.workplace.HiccWorkplaceVo;
import kr.co.hulan.aas.mvc.dao.mapper.AdminMsgInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.AirEnvironmentDao;
import kr.co.hulan.aas.mvc.dao.mapper.AttendanceBookDao;
import kr.co.hulan.aas.mvc.dao.mapper.HiccInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCoopDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceSafeyScoreDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWeatherDao;
import kr.co.hulan.aas.mvc.model.dto.AdminMsgInfoDto;
import kr.co.hulan.aas.mvc.model.dto.HiccInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiccWorkplaceService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private HiccInfoDao hiccInfoDao;

  @Autowired
  private WorkPlaceDao workPlaceDao;

  @Autowired
  private WorkPlaceCoopDao workPlaceCoopDao;

  @Autowired
  private WorkPlaceSafeyScoreDao workPlaceSafeyScoreDao;

  @Autowired
  private AirEnvironmentDao airEnvironmentDao;

  @Autowired
  private AdminMsgInfoDao adminMsgInfoDao;

  @Autowired
  private AttendanceBookDao attendanceBookDao;

  @Autowired
  private WorkPlaceWeatherDao workPlaceWeatherDao;

  public List<HiccWorkplaceSummaryVo> findSupportedWorkplaceByAccount(){
    return workPlaceDao.findSupportedWorkplaceByAccount(AuthenticationHelper.addAdditionalConditionByLevel());
  }

  public HiccNationWideInfoResponse findNationWideInfo(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();

    HiccInfoDto hiccInfo = hiccInfoDao.findHiccInfoByLoginUser(condition);
    if( hiccInfo == null ){
      hiccInfo = hiccInfoDao.findHiccInfo(1); // 기본 정보 가져오기
      if( hiccInfo == null ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "전국현황 표시를 위한 통합관제 기본정보가 존재하지 않습니다.");
      }
    }
    condition.put("hiccNo", hiccInfo.getHiccNo());

    HiccNationWideWorkplaceAllVo nationWideVo = new HiccNationWideWorkplaceAllVo();
    nationWideVo.setTotalCoopCount(workPlaceCoopDao.countHiccWorkplaceCoopList(condition));

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
    nationWideVo.setIntegGroupList(integGroupList);

    AttendanceWeekHistoryVo weekHistoryVo = getAttendanceWeekHistory();
    weekHistoryVo.setTotalWorkerCount(nationWideVo.getTotalWorkerCount());

    return HiccNationWideInfoResponse.builder()
        .mapUrl(hiccInfo.getMapUrl())
        .nationWide(nationWideVo)
        .sidoWeathers(airEnvironmentDao.findSidoWeatherList())
        .stateIndicator(workPlaceDao.findNationWideState(condition))
        .attendanceWeekState(weekHistoryVo)
        .build();
  }

  public HiccNationWideInfoResponse findNationWideWorkplaceInfo(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();

    HiccInfoDto hiccInfo = hiccInfoDao.findHiccInfoByLoginUser(condition);
    if( hiccInfo == null ){
      hiccInfo = hiccInfoDao.findHiccInfo(1); // 기본 정보 가져오기
      if( hiccInfo == null ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "전국현황 표시를 위한 통합관제 기본정보가 존재하지 않습니다.");
      }
    }
    condition.put("hiccNo", hiccInfo.getHiccNo());

    HiccNationWideWorkplaceAllVo nationWideVo = new HiccNationWideWorkplaceAllVo();
    nationWideVo.setTotalCoopCount(workPlaceCoopDao.countHiccWorkplaceCoopList(condition));

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
    nationWideVo.setIntegGroupList(integGroupList);
    return HiccNationWideInfoResponse.builder()
        .mapUrl(hiccInfo.getMapUrl())
        .nationWide(nationWideVo)
        .sidoWeathers(airEnvironmentDao.findSidoWeatherList())
        .build();
  }

  public HiccNationWideInfoResponse findNationWideStat(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();

    HiccInfoDto hiccInfo = hiccInfoDao.findHiccInfoByLoginUser(condition);
    if( hiccInfo == null ){
      hiccInfo = hiccInfoDao.findHiccInfo(1); // 기본 정보 가져오기
      if( hiccInfo == null ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "전국현황 표시를 위한 통합관제 기본정보가 존재하지 않습니다.");
      }
    }

    HiccNationWideStateIndicatorVo stateIndicator = workPlaceDao.findNationWideState(condition);
    AttendanceWeekHistoryVo weekHistoryVo = getAttendanceWeekHistory();
    weekHistoryVo.setTotalWorkerCount(stateIndicator.getTotalWorkerCount());

    return HiccNationWideInfoResponse.builder()
        .stateIndicator(stateIndicator)
        .attendanceWeekState(weekHistoryVo)
        .build();
  }

  public AttendanceWeekHistoryVo getAttendanceWeekHistory(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    Date currentDate  = new Date();
    Date before7Date = DateUtils.addDays(currentDate, -7);
    Date before14Date = DateUtils.addDays(currentDate, -14);
    condition.put("endDate", currentDate);
    condition.put("startDate", before7Date);
    List<AttendanceDayStatVo> oneWeek = attendanceBookDao.findDailyAttendanceStat(condition);

    condition.put("endDate", before7Date);
    condition.put("startDate", before14Date);
    List<AttendanceDayStatVo> twoWeek = attendanceBookDao.findDailyAttendanceStat(condition);
    return AttendanceWeekHistoryVo.builder()
        .oneWeekAgoWorkerCountList(oneWeek)
        .twoWeekAgoWorkerCountList(twoWeek)
        .build();
  }

  public List<HiccWorkplaceSummaryPerSidoVo> findWorkplaceStateListPerSido(){
    return workPlaceDao.findWorkplaceSummaryPerSido(AuthenticationHelper.addAdditionalConditionByLevel());
  }

  public List<HiccWorkplaceSummaryPerSigunguVo> findWorkplaceStateListBySido(String sidoCd){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel();
    condition.put("sidoCd", sidoCd);
    return workPlaceDao.findWorkplaceSummaryByRegion(condition);
  }

  public HiccStateIndicatorVo findTotalWorkplaceState(){
    HiccStateIndicatorVo workplaceState = workPlaceDao.findTotalWorkplaceState(AuthenticationHelper.addAdditionalConditionByLevel());
    if( workplaceState == null ){
      workplaceState = new HiccStateIndicatorVo();
      workplaceState.resetNullData();
    }
    return workplaceState;
  }

  public HiccWorkplaceStateResponse findWorkplaceState(String wpId){
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
    HiccWorkplaceStateVo workplaceState = workPlaceDao.findWorkplaceState(condition);
    if( workplaceState == null ) {
      workplaceState = new HiccWorkplaceStateVo();
      workplaceState.setWpId(wpId);
    }
    workplaceState.resetNullData();

    return HiccWorkplaceStateResponse.builder()
        .info(modelMapper.map(info, HiccWorkplaceVo.class))
        .state(workplaceState)
        .evaluation(evaluationVo)
        .weather(workPlaceWeatherDao.findHiccWeatherInfo(wpId))
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
      workplaceState.setWpId(wpId);
    }
    workplaceState.resetNullData();

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
