package kr.co.hulan.aas.mvc.api.sensorMgr.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.CreateSensorDistrictRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorSituationByDistrictRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.UpdateSensorDistrictRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorSituationByDistrictResponse;
import kr.co.hulan.aas.mvc.dao.mapper.SensorDistrictDao;
import kr.co.hulan.aas.mvc.dao.repository.SensorDistrictRepository;
import kr.co.hulan.aas.mvc.dao.repository.SensorInfoRepository;
import kr.co.hulan.aas.mvc.model.domain.SensorDistrict;
import kr.co.hulan.aas.mvc.model.dto.SensorDistrictDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorSituationByDistrictService {

    @Autowired
    private SensorInfoRepository sensorInfoRepository;

    @Autowired
    private SensorDistrictRepository sensorDistrictRepository;

    @Autowired
    private SensorDistrictDao sensorDistrictDao;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListSensorSituationByDistrictResponse findListPage(ListSensorSituationByDistrictRequest request) {
        return new ListSensorSituationByDistrictResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<SensorDistrictDto> findListByCondition(Map<String, Object> conditionMap) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return sensorDistrictDao.findSensorSituationListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream().map(sensorDistrictDto -> modelMapper.map(sensorDistrictDto, SensorDistrictDto.class)).collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return sensorDistrictDao.findSensorSituationListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public SensorDistrictDto findById(int siIdx) {
        SensorDistrictDto sensorDistrictDto = sensorDistrictDao.findById(siIdx);
        if (sensorDistrictDto != null) {
            return sensorDistrictDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("mybatisTransactionManager")
    public void create(CreateSensorDistrictRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        SensorDistrictDto saveDto = modelMapper.map(request, SensorDistrictDto.class);
        long existsCount = sensorDistrictRepository.countByWpIdAndSdName(saveDto.getWpId(), saveDto.getSdName());
        if( existsCount > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 구역입니다.");
        }
        sensorDistrictDao.create(saveDto);
    }

    @Transactional("mybatisTransactionManager")
    public void update(UpdateSensorDistrictRequest request, int sdIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getSdIdx() != sdIdx) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        SensorDistrictDto saveDto = modelMapper.map(request, SensorDistrictDto.class);

        Optional<SensorDistrict> currentInfo = sensorDistrictRepository.findById( saveDto.getSdIdx());
        if( currentInfo.isPresent() ){
            sensorDistrictDao.update(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 구역입니다.");
        }

    }

    @Transactional("transactionManager")
    public int delete(int sdIdx) {
        long count = sensorInfoRepository.countBySdIdx(sdIdx);
        if( count > 0 ){
            SensorDistrictDto dto = sensorDistrictDao.findById(sdIdx);
            if( dto != null ){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), dto.getSdName()+" 구역에 센서가 존재하여 구역 삭제가 불가능합니다");
            }
            else {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "구역이 존재하지 않습니다.");
            }

        }
        sensorDistrictRepository.deleteById(sdIdx);
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<Integer> sdIdxs) {
        int deleteCnt = 0;
        for (Integer sdIdx : sdIdxs) {
            deleteCnt += delete(sdIdx);
        }
        return deleteCnt;
    }
}
