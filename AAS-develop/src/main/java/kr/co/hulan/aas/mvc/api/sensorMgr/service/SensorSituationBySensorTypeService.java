package kr.co.hulan.aas.mvc.api.sensorMgr.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorSituationBySensorTypeRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorSituationBySensorTypeResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorSituationByWorkPlaceResponse;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationBySensorTypeDto;
import kr.co.hulan.aas.mvc.api.sensorMgr.dto.SensorSituationByWorkplaceDto;
import kr.co.hulan.aas.mvc.dao.mapper.SensorInfoDao;
import kr.co.hulan.aas.mvc.model.dto.SensorDistrictDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SensorSituationBySensorTypeService {

    @Autowired
    private SensorInfoDao sensorInfoDao;


    public ListSensorSituationBySensorTypeResponse findListPage(ListSensorSituationBySensorTypeRequest request) {
        return new ListSensorSituationBySensorTypeResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<SensorSituationBySensorTypeDto> findListByCondition(Map<String, Object> conditionMap) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return sensorInfoDao.findSensorSituationListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(sensorInfo -> modelMapper.map(sensorInfo, SensorSituationBySensorTypeDto.class)).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return sensorInfoDao.findSensorSituationListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    @Transactional("transactionManager")
    public int delete(int siType) {
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), " 해당 유형의 센서가 존재하여 삭제가 불가능합니다");

        /*
        long count = sensorInfoRepository.countBySdIdx(sdIdx);
        if( count > 0 ){
            SensorDistrictDto dto = sensorDistrictDao.findById(sdIdx);
            if( dto != null ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), dto.getSdName()+" 해당 유형의 센서가 존재하여 삭제가 불가능합니다");
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "구역이 존재하지 않습니다.");
            }

        }
        sensorDistrictRepository.deleteById(sdIdx);
        return 1;
         */
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<Integer> siTypes) {
        int deleteCnt = 0;
        for (Integer siType : siTypes) {
            deleteCnt += delete(siType);
        }
        return deleteCnt;
    }
}
