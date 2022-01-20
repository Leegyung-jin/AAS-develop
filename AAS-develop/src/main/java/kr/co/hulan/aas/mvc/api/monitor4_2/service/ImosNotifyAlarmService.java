package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosNotifyAlarmRequest;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.service.push.PushData;
import kr.co.hulan.aas.mvc.service.push.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImosNotifyAlarmService {

  @Autowired
  private PushService pushService;

  @Autowired
  private G5MemberDao g5MemberDao;

  public void sendAlarm(String wpId, ImosNotifyAlarmRequest request){
    PushData push = new PushData();
    push.setSubject( request.getPuSubject());
    push.setBody( request.getPuContent());
    push.setCode( "warn" );

    Map<String,Object> condition = new HashMap<String,Object>();
    condition.put("wpId", wpId);
    condition.put("attendance", "1");

    List<G5MemberDto> noticeTargetList = g5MemberDao.findWorkplaceWorkerListForNoticeByCondition(condition);
    for(G5MemberDto dto : noticeTargetList){
      if( StringUtils.equals(dto.getPushNormal(), "1")){
        push.addTargetInfo(dto.getMbId(), wpId, dto.getDeviceId(), dto.getAppVersion());
      }
    }

    if( push.targetInfoSize() > 0 ){
      pushService.sendPush(push);
    }
  }
}
