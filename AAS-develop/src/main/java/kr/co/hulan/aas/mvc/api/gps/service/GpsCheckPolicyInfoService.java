package kr.co.hulan.aas.mvc.api.gps.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.gps.controller.request.CreateGpsCheckPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.ListGpsCheckPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.request.UpdateGpsCheckPolicyInfoRequest;
import kr.co.hulan.aas.mvc.api.gps.controller.response.ListGpsCheckPolicyInfoResponse;
import kr.co.hulan.aas.mvc.dao.mapper.GpsCheckPolicyInfoDao;
import kr.co.hulan.aas.mvc.dao.repository.GpsCheckPolicyInfoRepository;
import kr.co.hulan.aas.mvc.dao.repository.WorkPlaceRepository;
import kr.co.hulan.aas.mvc.model.domain.GpsCheckPolicyInfo;
import kr.co.hulan.aas.mvc.model.domain.WorkPlace;
import kr.co.hulan.aas.mvc.model.dto.GpsCheckPolicyInfoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GpsCheckPolicyInfoService {

    @Autowired
    private GpsCheckPolicyInfoDao gpsCheckPolicyInfoDao;

    @Autowired
    private GpsCheckPolicyInfoRepository gpsCheckPolicyInfoRepository;

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    public ListGpsCheckPolicyInfoResponse findListPage(ListGpsCheckPolicyInfoRequest request) {
        return new ListGpsCheckPolicyInfoResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    public List<GpsCheckPolicyInfoDto> findListByCondition(Map<String, Object> conditionMap) {
        return gpsCheckPolicyInfoDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return gpsCheckPolicyInfoDao.findListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public GpsCheckPolicyInfoDto findById(int idx) {
        GpsCheckPolicyInfoDto gpsCheckPolicyInfoDto = gpsCheckPolicyInfoDao.findById(idx);
        if (gpsCheckPolicyInfoDto != null) {
            return gpsCheckPolicyInfoDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    public GpsCheckPolicyInfoDto findByWpId(String wpId) {
        GpsCheckPolicyInfoDto gpsCheckPolicyInfoDto = gpsCheckPolicyInfoDao.findByWpId(wpId);
        if (gpsCheckPolicyInfoDto != null) {
            return gpsCheckPolicyInfoDto;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int create(CreateGpsCheckPolicyInfoRequest request){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if( gpsCheckPolicyInfoRepository.countByWpId( request.getWpId() ) > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장의 GPS 정책이 이미 존재합니다.");
        }

        Optional<WorkPlace> workPlaceOp = workPlaceRepository.findById(request.getWpId());
        if( !workPlaceOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        GpsCheckPolicyInfo saveDto = modelMapper.map(request, GpsCheckPolicyInfo.class);
        saveDto.setUpdDatetime(new Date());
        saveDto.setUpdater( loginUser.getUsername() );
        gpsCheckPolicyInfoRepository.save(saveDto);
        return saveDto.getIdx();
    }

    @Transactional("transactionManager")
    public void update(UpdateGpsCheckPolicyInfoRequest request, int idx){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if ( request.getIdx() != idx ) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        if( gpsCheckPolicyInfoRepository.countByWpIdAndIdxNot( request.getWpId() , request.getIdx()) > 0 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "해당 현장의 GPS 정책이 이미 존재합니다.");
        }

        Optional<WorkPlace> workPlaceOp = workPlaceRepository.findById(request.getWpId());
        if( !workPlaceOp.isPresent()){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 현장입니다.");
        }

        Optional<GpsCheckPolicyInfo>  gpsCheckPolicyInfoOp = gpsCheckPolicyInfoRepository.findById(idx);
        if( gpsCheckPolicyInfoOp.isPresent() ){
            GpsCheckPolicyInfo currentInfo = gpsCheckPolicyInfoOp.get();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            modelMapper.map(request, currentInfo);

            currentInfo.setUpdDatetime(new Date());
            currentInfo.setUpdater( loginUser.getUsername() );

            gpsCheckPolicyInfoRepository.save(currentInfo);
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 GPS 정책입니다.");
        }
    }

    @Transactional("transactionManager")
    public void delete(int idx){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        gpsCheckPolicyInfoRepository.deleteById(idx);
    }


    @Transactional("transactionManager")
    public void deleteMultiple(List<Integer> idxs) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        for (Integer idx : idxs) {
            gpsCheckPolicyInfoRepository.deleteById(idx);
        }
    }
}
