package kr.co.hulan.aas.mvc.api.monitor4_2.service;

import java.util.List;
import kr.co.hulan.aas.mvc.api.board.service.NoticeBoardService;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceMainResponse;
import kr.co.hulan.aas.mvc.api.monitor4_1.controller.request.MonitorMainRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.request.ImosWorkplaceMainRequest;
import kr.co.hulan.aas.mvc.api.monitor4_2.controller.response.ImosWorkplaceMainResponse;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosMemberUiComponentVo;
import kr.co.hulan.aas.mvc.api.workplace.service.WorkplaceService;
import kr.co.hulan.aas.mvc.dao.mapper.AirEnvironmentDao;
import kr.co.hulan.aas.mvc.service.weather.WeatherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImosService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private WorkplaceService workplaceService;

  @Autowired
  private NoticeBoardService noticeBoardService;

  @Autowired
  WeatherService weatherService;

  @Autowired
  AirEnvironmentDao airEnvironmentDao;

  @Autowired
  ImosMemberComponentService imosMemberComponentService;

  public ImosWorkplaceMainResponse findWorkplaceMainInfo(String wpId, ImosWorkplaceMainRequest request){
    ImosWorkplaceMainResponse res = new ImosWorkplaceMainResponse();
    if( request.isWorkplace() ){
      res.setMonitoringWorkplace(workplaceService.findWorkplaceSupportMonitoringInfo(wpId));
    }
    if( request.getDeployPage() != null ){
      List<ImosMemberUiComponentVo> imosComponentList =  imosMemberComponentService.findListByWpId(wpId, request.getDeployPage());
      if( request.getDeployPage() == 1 && ( imosComponentList == null || imosComponentList.size() == 0 ) ){
        imosMemberComponentService.createDefaultUiComponent(wpId, request.getDeployPage());
        imosComponentList = imosMemberComponentService.findListByWpId(wpId, request.getDeployPage());
      }
      res.setUiComponentList(imosComponentList);
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
}
