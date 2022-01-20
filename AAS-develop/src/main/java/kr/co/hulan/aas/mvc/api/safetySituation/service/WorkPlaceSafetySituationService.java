package kr.co.hulan.aas.mvc.api.safetySituation.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListWorkplaceSafetySituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListWorkplaceSafetySituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.WorkPlaceSafetySituationDto;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkPlaceSafetySituationService {

    @Autowired
    WorkPlaceRepository workPlaceRepository;

    @Autowired
    WorkPlaceDao workPlaceDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListWorkplaceSafetySituationResponse findListPage(ListWorkplaceSafetySituationRequest request) {
        return new ListWorkplaceSafetySituationResponse(
                findListCountByCondition( AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap() )),
                findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(request.getConditionMap()))
        );
    }

    public List<WorkPlaceSafetySituationDto> findListByCondition(Map<String,Object> conditionMap) {
        return workPlaceDao.findSafetySituationListByCondition(conditionMap)
                .stream().map(workPlaceDto -> modelMapper.map(workPlaceDto, WorkPlaceSafetySituationDto.class)).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workPlaceDao.findSafetySituationListCountByCondition(conditionMap);
    }

}
