package kr.co.hulan.aas.mvc.api.sensorMgr.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.UpdateSensorInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.UpdateSensorPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorPolicyInfoResponse;
import kr.co.hulan.aas.mvc.dao.mapper.SensorPolicyInfoDao;
import kr.co.hulan.aas.mvc.dao.repository.SensorPolicyInfoRepository;
import kr.co.hulan.aas.mvc.model.domain.SensorDistrict;
import kr.co.hulan.aas.mvc.model.domain.SensorInfo;
import kr.co.hulan.aas.mvc.model.domain.SensorPolicyInfo;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.SensorInfoDto;
import kr.co.hulan.aas.mvc.model.dto.SensorPolicyInfoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SensorPolicyService {

    @Autowired
    private SensorPolicyInfoDao sensorPolicyInfoDao;

    @Autowired
    private SensorPolicyInfoRepository sensorPolicyInfoRepository;

    public ListSensorPolicyInfoResponse findListPage(ListSensorPolicyInfoRequest request) {
        return new ListSensorPolicyInfoResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<SensorPolicyInfoDto> findListByCondition(Map<String, Object> conditionMap) {
        return sensorPolicyInfoDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return sensorPolicyInfoDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    public SensorPolicyInfoDto findById(int spIdx) {
        SensorPolicyInfoDto sensorPolicyInfoDto = sensorPolicyInfoDao.findById(spIdx);
        if (sensorPolicyInfoDto != null) {
            return sensorPolicyInfoDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("mybatisTransactionManager")
    public void update(UpdateSensorPolicyInfoRequest request, int spIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getSpIdx() != spIdx) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        SensorPolicyInfoDto saveDto = modelMapper.map(request, SensorPolicyInfoDto.class);
        saveDto.setMbId(loginUser.getUsername());

        SensorPolicyInfoDto existsInfo = sensorPolicyInfoDao.findById(spIdx);
        if (existsInfo != null) {
            sensorPolicyInfoDao.update(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("mybatisTransactionManager")
    public void reset(int spIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        SensorPolicyInfoDto existsInfo = sensorPolicyInfoDao.findById(spIdx);
        if (existsInfo != null) {
            existsInfo.setMbId(loginUser.getUsername());
            sensorPolicyInfoDao.resetPolicy(existsInfo);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }



}
