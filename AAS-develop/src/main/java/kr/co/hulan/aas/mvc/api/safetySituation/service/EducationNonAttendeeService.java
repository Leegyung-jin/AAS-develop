package kr.co.hulan.aas.mvc.api.safetySituation.service;

import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListEducationAttendeeRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.request.ListEducationnonAttendeeSituationRequest;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListEducationAttendeeResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.controller.response.ListEducationnonAttendeeSituationResponse;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.EducationNonAttendeeDto;
import kr.co.hulan.aas.mvc.api.safetySituation.dto.EducationNonAttendeeSituationDto;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceCoopDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceWorkerDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EducationNonAttendeeService {

    @Autowired
    private WorkPlaceCoopDao workPlaceCoopDao;

    @Autowired
    private WorkPlaceWorkerDao workPlaceWorkerDao;

    @Autowired
    ModelMapper modelMapper;

    public ListEducationnonAttendeeSituationResponse findListPage(ListEducationnonAttendeeSituationRequest request) {
        return new ListEducationnonAttendeeSituationResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<EducationNonAttendeeSituationDto> findListByCondition(Map<String,Object> conditionMap) {
        return workPlaceCoopDao.findEducationNonAttendeeSituationListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(workPlaceCoopDto -> modelMapper.map(workPlaceCoopDto, EducationNonAttendeeSituationDto.class)).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return workPlaceCoopDao.findEducationNonAttendeeSituationListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public ListEducationAttendeeResponse findWorkerListByAttendStatus(ListEducationAttendeeRequest request) {
        return new ListEducationAttendeeResponse(
                findEducationAttendeeListCountByCondition(request.getConditionMap()),
                findEducationAttendeeListByCondition(request.getConditionMap())
        );
    }

    public List<EducationNonAttendeeDto> findEducationAttendeeListByCondition(Map<String,Object> conditionMap) {
        return workPlaceWorkerDao.findEducationAttendeeListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(workPlaceWorkerDto -> modelMapper.map(workPlaceWorkerDto, EducationNonAttendeeDto.class)).collect(Collectors.toList());
    }

    private Long findEducationAttendeeListCountByCondition(Map<String,Object> conditionMap) {
        return workPlaceWorkerDao.findEducationAttendeeListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }
}
