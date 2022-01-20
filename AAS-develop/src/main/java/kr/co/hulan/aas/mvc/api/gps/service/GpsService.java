package kr.co.hulan.aas.mvc.api.gps.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.JsonUtil;
import kr.co.hulan.aas.config.properties.HttpClientProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.bls.controller.request.BleMonitoringDataForUserRequest;
import kr.co.hulan.aas.mvc.api.bls.service.BlsMonitoringService;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchAttendantRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchAttendantSituationRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsLocationHistoryRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsMonitoringDataRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.SearchGpsUserRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.*;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.service.FieldManagerService;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantSituationVo;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import kr.co.hulan.aas.mvc.api.monitor.service.CommonMonitoringService;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceService;
import kr.co.hulan.aas.mvc.dao.mapper.BlsMonitoringDao;
import kr.co.hulan.aas.mvc.dao.mapper.GpsCheckPolicyInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.GpsLocationDao;
import kr.co.hulan.aas.mvc.dao.mapper.GpsMonitoringDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkGeofenceInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import kr.co.hulan.aas.mvc.model.dto.GpsCheckPolicyInfoDto;
import kr.co.hulan.aas.mvc.model.dto.GpsLocationHistoryDto;
import kr.co.hulan.aas.mvc.model.dto.WorkGeofenceInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GpsService {

    private Logger logger = LoggerFactory.getLogger(GpsService.class);

    @Autowired
    WorkplaceService workplaceService;

    @Autowired
    FieldManagerService fieldManagerService;

    @Autowired
    HttpClientProperties httpClientProperties;

    @Autowired
    private CloseableHttpClient client;

    @Autowired
    WorkPlaceWorkerDao workPlaceWorkerDao;

    @Autowired
    WorkGeofenceInfoDao workGeofenceInfoDao;

    @Autowired
    GpsCheckPolicyInfoDao gpsCheckPolicyInfoDao;

    @Autowired
    GpsLocationDao gpsLocationDao;

    @Autowired
    CommonMonitoringService commonMonitoringService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    GpsMonitoringDao gpsMonitoringDao;

    @Autowired
    BlsMonitoringDao blsMonitoringDao;

    @Value(value = "${default.smart_monitor.expired_gps}")
    int expired_gps_minute;

    @Data
    static class HttpCommand {
        private HttpPost postRequest;
        private HttpResponse response;
    }


    public Map searchGps(SearchGpsInfoRequest request) {

        WorkPlaceDto dto = workplaceService.findById(request.getWpId());
        if( dto == null ){
            throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "존재하지 않는 현장입니다.");
        }
        FieldManagerDto manager = fieldManagerService.findFieldManagerByMbId(dto.getManMbId());
        if( manager != null ){
            request.setMbId(StringUtils.replace(manager.getTelephone(), "-", ""));
            //request.setMbId(manager.getTelephone());
        }

        final HttpCommand httpCommand = new HttpCommand();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

            HttpPost postRequest = new HttpPost(httpClientProperties.getGpsRequestUrl()); //POST 메소드 URL 새성

            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Content-Type", "application/json;charset=UTF-8");

            postRequest.setEntity(new StringEntity(mapper.writeValueAsString(request), Consts.UTF_8)); //json 메시지 입력

            httpCommand.setPostRequest(postRequest);
            HttpResponse response = client.execute(postRequest);
            httpCommand.setResponse(response);
            if( response != null && response.getStatusLine() != null ){
                String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                return JsonUtil.toStringMap(result);
            }
        } catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().getSimpleName()+"|sendRequest|500|INTERNAL SERVER ERROR OCCURED", e);
        } finally{
            HttpClientUtils.closeQuietly(httpCommand.getResponse());
        }
        throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "GPS 정보를 가져올 수 없습니다.");
    }

    public Map searchGpsMonitoringDataRequest(SearchGpsMonitoringDataRequest request){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        MemberLevel level = MemberLevel.get(loginUser.getMbLevel());
        if( level == MemberLevel.FIELD_MANAGER  ){
            if( !StringUtils.equals(loginUser.getWpId(), request.getWpId()) ){
                throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
            }
        }
        else if( level != MemberLevel.SUPER_ADMIN  ){
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        WorkPlaceDto dto = workplaceService.findById(request.getWpId());
        if( dto == null ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        Map<String,Object> resultMap = new HashMap<String,Object>();

        Map<String,Object> condition = request.getConditionMap();
        // condition.put("EXPIRED_GPS_MINUTE", expired_gps_minute);
        // 장비 맵핑되지 않은 근로자
        condition.put("EQUIPMENT", "NOT_EQUIP");
        List<Map<String, Object>> worker_list = gpsLocationDao.selectGpsLocationWorker(condition);
        resultMap.put("worker_list", worker_list != null && worker_list.size() > 0 ? worker_list : Collections.emptyList());

        // 장비기사
        condition.put("EQUIPMENT", "EQUIP");
        List<Map<String, Object>>  equip_list = gpsLocationDao.selectGpsLocationWorker(condition);
        resultMap.put("equip_list", equip_list != null && equip_list.size() > 0 ? equip_list : Collections.emptyList());

        List<Map<String, Object>>  device_list = gpsLocationDao.selectGpsLocationEquipment(condition);
        resultMap.put("device_list", device_list != null && device_list.size() > 0 ? device_list : Collections.emptyList());

        if( StringUtils.isNotEmpty( request.getMarkerMbId())){
            List<Map<String,Object>> screenUserList = new ArrayList<Map<String,Object>>();
            screenUserList.addAll( worker_list.stream().filter( worker -> StringUtils.equals( String.valueOf(worker.get("mb_id")), request.getMarkerMbId())).collect(Collectors.toList()) );
            screenUserList.addAll( equip_list.stream().filter( worker -> StringUtils.equals( String.valueOf(worker.get("mb_id")), request.getMarkerMbId())).collect(Collectors.toList()) );
            resultMap.put("marker_list", screenUserList);
        }

        resultMap.put("current_worker_count", gpsMonitoringDao.findCurrentWorkerCount(request.getWpId()));
            /* 공통정보 */
        Map<String,Object> commonResultInfo = commonMonitoringService.getCommonMonitoringInfo(dto);
        resultMap.putAll(commonResultInfo);
        return resultMap;
    }

    public SearchAttendantResponse searchAttendant(SearchAttendantRequest request){
        return new SearchAttendantResponse(
                findAttendantListCountByCondition(request.getConditionMap()),
                findAttendantListByCondition(request.getConditionMap())
        );
    }


    public List<AttendantVo> findAttendantListByCondition(Map<String, Object> conditionMap) {
        return workPlaceWorkerDao.findAttendantListByCondition(conditionMap)
                .stream().map(workPlaceWorker -> modelMapper.map(workPlaceWorker, AttendantVo.class) ).collect(Collectors.toList());
    }

    private Long findAttendantListCountByCondition(Map<String, Object> conditionMap) {
        return workPlaceWorkerDao.findAttendantListCountByCondition(conditionMap);
    }

    public SearchAttendantSituationResponse searchAttendantSituation(SearchAttendantSituationRequest request){
        return new SearchAttendantSituationResponse(
                findAttendantSituationListCountByCondition(request.getConditionMap()),
                findAttendantSituationListByCondition(request.getConditionMap())
        );
    }

    public List<AttendantSituationVo> findAttendantSituationListByCondition(Map<String, Object> conditionMap) {
        return workPlaceWorkerDao.findAttendantSituationListByCondition(conditionMap)
                .stream().map(workPlaceWorker -> modelMapper.map(workPlaceWorker, AttendantSituationVo.class) ).collect(Collectors.toList());
    }


    private Long findAttendantSituationListCountByCondition(Map<String, Object> conditionMap) {
        return workPlaceWorkerDao.findAttendantSituationListCountByCondition(conditionMap);
    }

    public GeoFenceInfoResponse searchGeofenceList(String wpId){
        GpsCheckPolicyInfoDto policy = gpsCheckPolicyInfoDao.findByWpId(wpId);
        if( policy == null ){
            throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "GPS 정책이 존재하지 않습니다.");
        }
        WorkPlaceDto workPlaceDto = workplaceService.findById(wpId);
        if( workPlaceDto == null ){
            throw new CommonException(BaseCode.ERR_API_EXCEPTION.code(), "현장 정보가 존재하지 않습니다.");
        }

        Map<Integer, List<GpsLocation>> gpsLocation = new HashMap<Integer, List<GpsLocation>>();
        List<WorkGeofenceInfoDto> dtoList = workGeofenceInfoDao.findAll(wpId);
        for(WorkGeofenceInfoDto dto : dtoList ){
            List<GpsLocation> locationList =  gpsLocation.get(dto.getWpSeq());
            if( locationList == null ){
                locationList = new ArrayList<GpsLocation>();
                gpsLocation.put(dto.getWpSeq(), locationList );
            }
            locationList.add(new GpsLocation( dto.getLatitude(), dto.getLongitude()));
        }
        return new GeoFenceInfoResponse(
            workPlaceDto.getWpId(),
            workPlaceDto.getWpName(),
            new GpsLocation( policy.getGpsCenterLat(), policy.getGpsCenterLong() ),
            gpsLocation
        );
    }

    public List<GpsLocationHistoryDto> searchGpsLocationHistory(SearchGpsLocationHistoryRequest request){
        return gpsLocationDao.selectGpsLocationHistory(request.getConditionMap());
    }

    public List<GpsUserVo> searchGpsUser(SearchGpsUserRequest request){
        return gpsLocationDao.searchGpsUser(request.getConditionMap());
    }


    public List<AttendantSituationVo> findAttendantSituationListForMonitoring(Map<String, Object> conditionMap) {
        return gpsMonitoringDao.findAttendantSituationListForMonitoring(conditionMap);
    }

    public List<AttendantVo> findAttendantListForMonitoring(Map<String, Object> conditionMap) {
        return blsMonitoringDao.findAttendantListForMonitoring(conditionMap);
    }

    public List<AttendantVo> findCurrentAttendantListForMonitoring(Map<String, Object> conditionMap) {
        return gpsMonitoringDao.findCurrentAttendantListForMonitoring(conditionMap);
    }

}
