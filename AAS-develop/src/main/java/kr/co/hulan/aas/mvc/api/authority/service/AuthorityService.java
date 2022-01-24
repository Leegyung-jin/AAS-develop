package kr.co.hulan.aas.mvc.api.authority.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultPageResult;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityCreateRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityListRequest;
import kr.co.hulan.aas.mvc.api.authority.controller.request.AuthorityUpdateRequest;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityDto;
import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityUserDto;
import kr.co.hulan.aas.mvc.dao.mapper.AuthorityDao;
import kr.co.hulan.aas.mvc.dao.repository.AuthorityLevelRepository;
import kr.co.hulan.aas.mvc.dao.repository.AuthorityMbRepository;
import kr.co.hulan.aas.mvc.dao.repository.AuthorityRepository;
import kr.co.hulan.aas.mvc.model.domain.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityMbRepository authorityMbRepository;

    @Autowired
    private AuthorityLevelRepository authorityLevelRepository;

    public DefaultPageResult<AuthorityDto> findListPage(AuthorityListRequest request) {
        return DefaultPageResult.<AuthorityDto>builder()
                .currentPage(request.getPage())
                .pageSize(request.getPageSize())
                .totalCount(countListByCondition(request.getConditionMap()))
                .list(findListByCondition(request.getConditionMap()))
                .build();
    }

    public List<AuthorityDto> findListByCondition(Map<String, Object> conditionMap) {
        return authorityDao.findListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public Integer countListByCondition(Map<String, Object> conditionMap) {
        return authorityDao.countListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public AuthorityDto findInfo(String authorityId) {
        AuthorityDto currentVo = authorityDao.findInfo(authorityId);
        List<String> selectedLevelList = authorityDao.findSelectedLevelList(authorityId);
        List<AuthorityUserDto> usedAuthorityUserList = authorityDao.findUsedAuthorityUserList(authorityId);
//        Integer countfindUsedAuthorityUserList = authorityDao.countfindUsedAuthorityUserList(authorityId);

        // el-transfer
        currentVo.setSelectedLevelList(selectedLevelList);
        currentVo.setUsedAuthorityUserList(usedAuthorityUserList);

        if (currentVo != null ) {
            return currentVo;
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("transactionManager")
    public String create(AuthorityCreateRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Authority saveEntity = modelMapper.map(request, Authority.class);

        if(authorityRepository.existsByAuthorityId(saveEntity.getAuthorityId()) ){
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 해당 권한 아이디가 존재합니다.");
        }

        saveEntity.setCreator(loginUser.getMbId());
        saveEntity.setCreateDate(new Date());
        saveEntity.setUpdater(loginUser.getMbId());
        saveEntity.setUpdateDate(new Date());

        authorityRepository.save(saveEntity);

        // 등급 탭: 선택 적용된 등급
        if(request.getSelectedLevelList() != null && request.getSelectedLevelList().size() > 0){
            List<AuthorityLevel> saveLevelList = new ArrayList<>();
            for(Integer mbLevel : request.getSelectedLevelList()) {
                AuthorityLevel authLevel = new AuthorityLevel();
                authLevel.setMbLevel(mbLevel);
                authLevel.setAuthorityId(request.getAuthorityId());
                authLevel.setUpdater(loginUser.getMbId());
                authLevel.setUpdateDate(new Date());
                saveLevelList.add(authLevel);
            }
            authorityLevelRepository.saveAll(saveLevelList);
        }

        // 사용자 탭: 등록된 사용자 목록
        if(request.getAuthorityUserList() != null && request.getAuthorityUserList().size() > 0){
            List<AuthorityMb> saveAuthrityUserList = new ArrayList<>();
            List<String> authorityUserList = request.getAuthorityUserList();
            for(String mbId : authorityUserList) {
                AuthorityMb auMbList = new AuthorityMb();
                auMbList.setMbId(mbId);
                auMbList.setAuthorityId(request.getAuthorityId());
                auMbList.setUpdater(loginUser.getMbId());
                auMbList.setUpdateDate(new Date());
                saveAuthrityUserList.add(auMbList);
            }
            authorityMbRepository.saveAll(saveAuthrityUserList);
        }
        return saveEntity.getAuthorityName();
    }

    public void update(AuthorityUpdateRequest request, String authorityId, String mbId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Authority saveEntity = modelMapper.map(request, Authority.class);

        if( saveEntity == null ){
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "권한 정보가 존재하지 않습니다.");
        }

        modelMapper.map(request, saveEntity);
        saveEntity.setUpdater(loginUser.getMbId());
        saveEntity.setUpdateDate(new Date());

        authorityRepository.save(saveEntity);

        authorityLevelRepository.deleteByAuthorityId(authorityId);
        if(request.getSelectedLevelList() != null && request.getSelectedLevelList().size() > 0){
            List<AuthorityLevel> saveLevelList = new ArrayList<>();
            for(Integer mbLevel : request.getSelectedLevelList()) {
                AuthorityLevel authLevel = new AuthorityLevel();
                authLevel.setMbLevel(mbLevel);
                authLevel.setAuthorityId(request.getAuthorityId());
                authLevel.setUpdater(loginUser.getMbId());
                authLevel.setUpdateDate(new Date());
                saveLevelList.add(authLevel);
            }
            authorityLevelRepository.saveAll(saveLevelList);
        }

        authorityMbRepository.deleteByMbId(mbId);
        if(request.getAuthorityUserList() != null && request.getAuthorityUserList().size() > 0){
            List<AuthorityMb> saveAuthrityUserList = new ArrayList<>();
            List<String> authorityUserList = request.getAuthorityUserList();
            for(String memberId : authorityUserList) {
                AuthorityMb auMbList = new AuthorityMb();
                auMbList.setMbId(memberId);
                auMbList.setAuthorityId(request.getAuthorityId());
                auMbList.setUpdater(loginUser.getMbId());
                auMbList.setUpdateDate(new Date());
                saveAuthrityUserList.add(auMbList);
            }
            authorityMbRepository.saveAll(saveAuthrityUserList);
        }
    }

    @Transactional("transactionManager")
    public int delete(String authorityId) {
        Authority saveEntity = authorityRepository.findById(authorityId).orElse(null);
        if(saveEntity != null) {
            authorityRepository.delete(saveEntity);
            return 1;
        }
        return 0;
    }
}