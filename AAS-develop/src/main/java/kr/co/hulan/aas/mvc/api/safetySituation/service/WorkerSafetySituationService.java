package kr.co.hulan.aas.mvc.api.safetySituation.service;

import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkerSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkerSafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerSafetySituationDto;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogDao;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogInoutDao;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogTraceDao;
import kr.co.hulan.aas.mvc.model.dto.SensorLogDto;
import kr.co.hulan.aas.mvc.model.dto.SensorLogTraceDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkerSafetySituationService {

    @Autowired
    SensorLogInoutDao sensorLogInoutDao;

    @Autowired
    SensorLogTraceDao sensorLogTraceDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListWorkerSafetySituationResponse findListPage(ListWorkerSafetySituationRequest request) {
        return new ListWorkerSafetySituationResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<WorkerSafetySituationDto> findListByCondition(Map<String,Object> conditionMap) {
        return sensorLogInoutDao.findSafetySituationListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(sensorLogInoutDto -> modelMapper.map(sensorLogInoutDto, WorkerSafetySituationDto.class)).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return sensorLogInoutDao.findSafetySituationListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    public List<SensorLogTraceDto> findWorkerSafetySensorLogListByCondition(Map<String,Object> conditionMap) {
        return sensorLogTraceDao.findWorkerSafetySensorLogListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


}
