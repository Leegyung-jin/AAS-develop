package kr.co.hulan.aas.mvc.api.sensorMgr.service;

import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorSituationByWorkPlaceRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorSituationByWorkPlaceResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationByWorkplaceDto;
import kr.co.hulan.aas.mvc.dao.mapper.SensorInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkPlaceDao;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SensorSituationByWorkPlaceService {

    @Autowired
    private WorkPlaceDao workPlaceDao;

    public ListSensorSituationByWorkPlaceResponse findListPage(ListSensorSituationByWorkPlaceRequest request) {
        return new ListSensorSituationByWorkPlaceResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<SensorSituationByWorkplaceDto> findListByCondition(Map<String, Object> conditionMap) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return workPlaceDao.findSensorSituationListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
            .stream().map(workPlaceDto -> modelMapper.map(workPlaceDto, SensorSituationByWorkplaceDto.class)).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return workPlaceDao.findSensorSituationListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }
}
