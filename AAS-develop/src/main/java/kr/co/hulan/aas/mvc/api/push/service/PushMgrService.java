package kr.co.hulan.aas.mvc.api.push.service;

import kr.co.hulan.aas.mvc.api.push.controller.request.SendPushRequest;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.service.push.PushData;
import kr.co.hulan.aas.mvc.service.push.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushMgrService {

  @Autowired
  private PushService pushService;

  @Autowired
  private WorkPlaceWorkerDao workPlaceWorkerDao;

  public void sendPush(SendPushRequest request){
    List<G5MemberDto> memberDtoList = workPlaceWorkerDao.findWorkplaceWorkerForPush(request.getConditionMap());
    if(memberDtoList != null && memberDtoList.size() > 0 ){
      PushData pushData = new PushData();
      pushData.setSubject( request.getPuSubject());
      pushData.setBody( request.getPuContent());
      pushData.setCode( request.getPuChk() != null &&  request.getPuChk() == 1 ? "warn" : "notice");
      // pushData.setCode("notice_personal");
      // pushData.setCode("notice");

      for(G5MemberDto memberDto : memberDtoList ){
        if( StringUtils.equals(memberDto.getPushNormal(), "1") && StringUtils.isNotBlank(memberDto.getDeviceId())){
          pushData.addTargetInfo(memberDto.getMbId(), request.getWpId() ,memberDto.getDeviceId(), memberDto.getAppVersion());
        }
      }

      if( pushData.targetInfoSize() > 0 ){
        pushService.sendPush(pushData);
      }
    }
  }

}
