package kr.co.hulan.aas.mvc.api.level.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.level.controller.request.LevelCreateRequest;
import kr.co.hulan.aas.mvc.api.level.controller.request.LevelListRequest;
import kr.co.hulan.aas.mvc.api.level.controller.request.LevelUpdateRequest;
import kr.co.hulan.aas.mvc.dao.mapper.LevelDao;
import kr.co.hulan.aas.mvc.api.level.model.dto.LevelDto;
import kr.co.hulan.aas.mvc.dao.repository.LevelAuthorityRepository;
import kr.co.hulan.aas.mvc.dao.repository.LevelRepository;
import kr.co.hulan.aas.mvc.model.domain.AuthorityLevel;
import kr.co.hulan.aas.mvc.model.domain.Level;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LevelService {
    private Logger logger = LoggerFactory.getLogger(LevelService.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LevelRepository levelRepository;
    
    @Autowired
    private LevelAuthorityRepository levelAuthorityRepository;

    @Autowired
    private LevelDao levelDao;

    public DefaultPageResult<LevelDto> findListPage(LevelListRequest request) {
        return DefaultPageResult.<LevelDto>builder()
                .currentPage(request.getPage())
                .pageSize(request.getPageSize())
                .totalCount(countListByCondition(request.getConditionMap()))
                .list(findListByCondition(request.getConditionMap()))
                .build();
    }

    public List<LevelDto> findListByCondition(Map<String, Object> conditionMap) {
        return levelDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public Integer countListByCondition(Map<String, Object> conditionMap) {
        return levelDao.countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public LevelDto findInfo(Integer mbLevel) {
        LevelDto currentVo = levelDao.findInfo(mbLevel);
        List<String> selectedAuthorityList = levelDao.findSelectedAuthorityList(mbLevel);


        // el-transfer
        currentVo.setSelectedAuthorityList(selectedAuthorityList);

        if (currentVo != null ) {
            return currentVo;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("transactionManager")
    public Integer create(LevelCreateRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Level saveEntity = modelMapper.map(request, Level.class);

        if(levelRepository.existsByMbLevel(saveEntity.getMbLevel()) ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 등급 코드가 존재합니다.");
        }

        saveEntity.setCreator(loginUser.getMbId());
        saveEntity.setCreateDate(new Date());
        saveEntity.setUpdater(loginUser.getMbId());
        saveEntity.setUpdateDate(new Date());

        levelRepository.save(saveEntity);

        if( request.getSelectedAuthorityList() != null && request.getSelectedAuthorityList().size() > 0){
            List<AuthorityLevel> saveAuthorityList = new ArrayList<>();
            for( String authorityId : request.getSelectedAuthorityList()) {
                AuthorityLevel auth = new AuthorityLevel();
                auth.setAuthorityId(authorityId);
                auth.setMbLevel(request.getMbLevel());
                auth.setUpdater(loginUser.getMbId());
                auth.setUpdateDate(new Date());
                saveAuthorityList.add(auth);
            }
            levelAuthorityRepository.saveAll(saveAuthorityList);
        }
        return saveEntity.getMbLevel();
    }

    @Transactional("transactionManager")
    public void update(LevelUpdateRequest request, Integer mbLevel) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Level saveEntity = levelRepository.findById(mbLevel).orElse(null);

        if( saveEntity == null ){
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "회원 정보가 존재하지 않습니다.");
        }

        modelMapper.map(request, saveEntity);
        saveEntity.setUpdater(loginUser.getMbId());
        saveEntity.setUpdateDate(new Date());

        levelRepository.save(saveEntity);

        levelAuthorityRepository.deleteByMbLevel(mbLevel);
        if( request.getSelectedAuthorityList() != null && request.getSelectedAuthorityList().size() > 0){
            List<AuthorityLevel> saveAuthorityList = new ArrayList<>();
            for( String authorityId : request.getSelectedAuthorityList()) {
                AuthorityLevel auth = new AuthorityLevel();
                auth.setAuthorityId(authorityId);
                auth.setMbLevel(request.getMbLevel());
                auth.setUpdater(loginUser.getMbId());
                auth.setUpdateDate(new Date());
                saveAuthorityList.add(auth);
            }
            levelAuthorityRepository.saveAll(saveAuthorityList);
        }
    }

    @Transactional("transactionManager")
    public int delete(Integer mbLevel) {
        if ( mbLevel == 10 ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "최고 관리자는 삭제할 수 없습니다.");
        }
        Level saveEntity = levelRepository.findById(mbLevel).orElse(null);
        if(saveEntity != null) {
            levelRepository.delete(saveEntity);
            return 1;
        }
        return 0;
    }
}
