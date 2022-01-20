package kr.co.hulan.aas.mvc.api.sensorMgr.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.CreateSensorInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.ListSensorInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.request.UpdateSensorInfoRequest;
import kr.co.hulan.aas.mvc.api.sensorMgr.controller.response.ListSensorInfoResponse;
import kr.co.hulan.aas.mvc.dao.mapper.SensorInfoDao;
import kr.co.hulan.aas.mvc.dao.mapper.SensorPolicyInfoDao;
import kr.co.hulan.aas.mvc.dao.repository.SensorDistrictRepository;
import kr.co.hulan.aas.mvc.dao.repository.SensorInfoRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.*;
import kr.co.hulan.aas.mvc.model.dto.SensorInfoDto;
import kr.co.hulan.aas.mvc.model.dto.SensorPolicyInfoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SensorManageService {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private SensorDistrictRepository sensorDistrictRepository;

    @Autowired
    private SensorInfoRepository sensorInfoRepository;

    @Autowired
    private SensorInfoDao sensorInfoDao;

    @Autowired
    private SensorPolicyInfoDao sensorPolicyInfoDao;


    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    public ListSensorInfoResponse findListPage(ListSensorInfoRequest request) {
        return new ListSensorInfoResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<SensorInfoDto> findListByCondition(Map<String, Object> conditionMap) {
        return sensorInfoDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return sensorInfoDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }


    public SensorInfoDto findById(int siIdx) {
        SensorInfoDto sensorInfoDto = sensorInfoDao.findById(siIdx);
        if (sensorInfoDto != null) {
            return sensorInfoDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    public boolean checkUsableSiCode(String wpId, String siCode, int siIdx){
        if( siIdx != 0 ){
            return sensorInfoRepository.countByWpIdAndSiCodeEqualsAndSiIdxNot( wpId, siCode, siIdx) == 0;
        }
        else {
            return sensorInfoRepository.countByWpIdAndSiCode(wpId, siCode) == 0 ;
        }

    }


    @Transactional("mybatisTransactionManager")
    public void create(CreateSensorInfoRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        SensorInfoDto saveDto = modelMapper.map(request, SensorInfoDto.class);

        Optional<SensorDistrict> districtOp = sensorDistrictRepository.findById(saveDto.getSdIdx());
        if( districtOp.isPresent()){
            saveDto.setSdName( districtOp.get().getSdName());
            saveDto.setWpId( districtOp.get().getWpId());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 구역입니다.");
        }

        Optional<WorkPlace>  workplaceOp = workPlaceRepository.findById(saveDto.getWpId());
        if( workplaceOp.isPresent() ){
            saveDto.setWpName(workplaceOp.get().getWpName());
            saveDto.setMajor(workplaceOp.get().getWpIdx());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }


        SensorPolicyInfoDto policyDto = getSensorPolicy(saveDto.getWpId(), saveDto.getMinor());
        if( policyDto == null ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "minor identifier 에 맞는 센서 유형이 존재하지 않습니다.");
        }
        saveDto.setSiType(policyDto.getSiType());
        saveDto.makeSiCode();

        if( !checkUsableSiCode(saveDto.getWpId(), saveDto.getSiCode(), 0) ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 센서 코드입니다.");
        }

        sensorInfoDao.create(saveDto);

    }

    private SensorPolicyInfoDto getSensorPolicy(String wpId, int minor){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("wpId", wpId);
        condition.put("minor", minor);
        return sensorPolicyInfoDao.findByRange(condition);
    }


    @Transactional("mybatisTransactionManager")
    public void update(UpdateSensorInfoRequest request, int siIdx) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getSiIdx() != siIdx) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        SensorInfoDto saveDto = modelMapper.map(request, SensorInfoDto.class);

        Optional<SensorDistrict> districtOp = sensorDistrictRepository.findById(saveDto.getSdIdx());
        if( districtOp.isPresent()){
            saveDto.setSdName( districtOp.get().getSdName());
            saveDto.setWpId( districtOp.get().getWpId());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 구역입니다.");
        }

        Optional<WorkPlace>  workplaceOp = workPlaceRepository.findById(saveDto.getWpId());
        if( workplaceOp.isPresent() ){
            saveDto.setWpName(workplaceOp.get().getWpName());
            saveDto.setMajor(workplaceOp.get().getWpIdx());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        SensorPolicyInfoDto policyDto = getSensorPolicy(saveDto.getWpId(), saveDto.getMinor());
        if( policyDto == null ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "minor identifier 에 맞는 센서 유형이 존재하지 않습니다.");
        }
        saveDto.setSiType(policyDto.getSiType());
        saveDto.makeSiCode();

        Optional<SensorInfo> existsInfo = sensorInfoRepository.findById(siIdx);
        if (existsInfo.isPresent()) {

            if( !checkUsableSiCode( saveDto.getWpId(), saveDto.getSiCode(), saveDto.getSiIdx())){
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 센서 코드입니다.");
            }

            sensorInfoDao.update(saveDto);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }


    }


    @Transactional("transactionManager")
    public int delete(int siIdx) {
        sensorInfoRepository.deleteById(siIdx);
        return 1;
    }

    @Transactional("transactionManager")
    public int deleteMultiple(List<Integer> siIdxs) {
        int deleteCnt = 0;
        for (Integer siIdx : siIdxs) {
            deleteCnt += delete(siIdx);
        }
        return deleteCnt;
    }
}
