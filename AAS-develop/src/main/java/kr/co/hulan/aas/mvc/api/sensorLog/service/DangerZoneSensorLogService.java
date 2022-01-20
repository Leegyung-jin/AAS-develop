package kr.co.hulan.aas.mvc.api.sensorLog.service;

import kr.co.hulan.aas.common.code.SensorType;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.ListDangerZoneSensorLogRequest;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.request.NotifyAlarmRequest;
import kr.co.hulan.aas.mvc.api.sensorLog.controller.response.ListDangerZoneSensorLogResponse;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogDao;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.PushLogDto;
import kr.co.hulan.aas.mvc.model.dto.SensorLogDto;
import kr.co.hulan.aas.mvc.service.push.PushData;
import kr.co.hulan.aas.mvc.service.push.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DangerZoneSensorLogService {

    @Autowired
    private SensorLogDao sensorLogDao;

    @Autowired
    private PushService pushService;

    public ListDangerZoneSensorLogResponse findListPage(ListDangerZoneSensorLogRequest request) {
        return new ListDangerZoneSensorLogResponse(
                findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap())),
                findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
        );
    }


    public List<SensorLogDto> findListByCondition(Map<String,Object> conditionMap) {
        conditionMap.put("siType", SensorType.DANGER_ZONE.getName());
        return sensorLogDao.findListByCondition(conditionMap);
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        conditionMap.put("siType", SensorType.DANGER_ZONE.getName());
        return sensorLogDao.findListCountByCondition(conditionMap);
    }


    public List<SensorLogDto> findByIdList(List<Integer> slIdxs) {
        Map<String,Object> conditionMap = new HashMap<String,Object>();
        conditionMap.put("slIdxs", slIdxs);
        return sensorLogDao.findByIdList(conditionMap);
    }

    public void sendAlarm(NotifyAlarmRequest request){
        List<SensorLogDto> sensorLogList = findByIdList(request.getSlIdxs());
        PushData push = new PushData();
        push.setSubject( request.getPuSubject());
        push.setBody( request.getPuContent());
        push.setCode( request.getPuChk() == 1 ? "warn" : "");
        for( SensorLogDto logDto : sensorLogList ){
            if( logDto != null && logDto.isAllowedPushDanger() && StringUtils.isNotBlank(logDto.getDeviceId())){
                push.addTargetInfo(logDto.getMbId(), logDto.getWpId() ,logDto.getDeviceId(), logDto.getAppVersion());
            }
        }

        if( push.targetInfoSize() > 0 ){
            pushService.sendPush(push);
        }

    }
}
