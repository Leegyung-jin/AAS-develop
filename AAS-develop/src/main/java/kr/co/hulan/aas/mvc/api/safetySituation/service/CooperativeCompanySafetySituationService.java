package kr.co.hulan.aas.mvc.api.safetySituation.service;

import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListCooperativeCompanySafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListCooperativeCompanySafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.CooperativeCompanySafetySituationDto;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCoopDao;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceCoopRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CooperativeCompanySafetySituationService {

    @Autowired
    WorkPlaceCoopRepository workPlaceCoopRepository;

    @Autowired
    WorkPlaceCoopDao workPlaceCoopDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListCooperativeCompanySafetySituationResponse findListPage(ListCooperativeCompanySafetySituationRequest request) {
        return new ListCooperativeCompanySafetySituationResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<CooperativeCompanySafetySituationDto> findListByCondition(Map<String,Object> conditionMap) {
        return workPlaceCoopDao.findSafetySituationListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(workPlaceCoopDto -> modelMapper.map(workPlaceCoopDto, CooperativeCompanySafetySituationDto.class)).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workPlaceCoopDao.findSafetySituationListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }
}
