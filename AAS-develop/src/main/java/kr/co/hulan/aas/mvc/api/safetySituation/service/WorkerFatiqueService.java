package kr.co.hulan.aas.mvc.api.safetySituation.service;

import kr.co.hulan.aas.common.code.WorkerFatique;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkerFatiqueRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkerFatiqueResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkerFatiqueDto;
import kr.co.hulan.aas.mvc.dao.mapper.SensorLogInoutDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceWorkerDto;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkerFatiqueService {

    @Autowired
    SensorLogInoutDao sensorLogInoutDao;

    @Autowired
    WorkPlaceWorkerDao workPlaceWorkerDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListWorkerFatiqueResponse findListPage(ListWorkerFatiqueRequest request) {
        ListWorkerFatiqueResponse response = findWorkerFatiqueListPerTypeCountByCondition(request.getConditionMap());
        response.setList(findListByCondition(request.getConditionMap()));
        return response;
    }

    public List<WorkerFatiqueDto> findListByCondition(Map<String,Object> conditionMap) {
        return workPlaceWorkerDao.findWorkerFatiqueListByCondition( AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(sensorLogInoutDto -> modelMapper.map(sensorLogInoutDto, WorkerFatiqueDto.class)).collect(Collectors.toList());
    }

    /*
    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workPlaceWorkerDao.findWorkerFatiqueListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }
     */

    private ListWorkerFatiqueResponse findWorkerFatiqueListPerTypeCountByCondition(Map<String,Object> conditionMap) {
        return workPlaceWorkerDao.findWorkerFatiqueListPerTypeCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

}
