package kr.co.hulan.aas.mvc.api.member.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.config.properties.LoginPolicyProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.member.controller.request.*;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.SuperAdminDto;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListSuperAdminResponse;
import kr.co.hulan.aas.mvc.api.member.dto.WorkerDto;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuperAdminService {

    @Autowired
    MbKeyGenerateService mbKeyGenerateService;

    @Autowired
    G5MemberRepository g5MemberRepository;

    @Autowired
    G5MemberDao g5MemberDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager entityManager;

    @Autowired
    MysqlPasswordEncoder mysqlPasswordEncoder;

    @Autowired
    LoginPolicyProperties loginPolicyProperties;

    public ListSuperAdminResponse findListPage(ListSuperAdminRequest request) {
        return new ListSuperAdminResponse(
                findListCountByCondition(request.getConditionMap()),
                findWithdrawListCountByCondition(request.getConditionMap()),
                findBlockListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }


    private List<SuperAdminDto> findListByCondition(Map<String, Object> conditionMap) {
        return g5MemberDao.findSuperAdminListByCondition(conditionMap)
                .stream()
                .map(g5MemberDto -> {
                    SuperAdminDto dto = modelMapper.map(g5MemberDto, SuperAdminDto.class);
                    dto.setLocked( loginPolicyProperties.isLocked(g5MemberDto) );
                    dto.setPasswordExpired( loginPolicyProperties.isPasswordExpired(g5MemberDto) );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String, Object> conditionMap) {
        return g5MemberDao.findSuperAdminListCountByCondition(conditionMap);
    }


    private Long findWithdrawListCountByCondition(Map<String, Object> conditionMap) {
        conditionMap.put("withdraw", "withdraw");
        return findListCountByCondition(conditionMap);
    }

    private Long findBlockListCountByCondition(Map<String, Object> conditionMap) {
        conditionMap.put("block", "block");
        return findListCountByCondition(conditionMap);
    }

    public SuperAdminDto findByMbId(String mbId) {
        Optional<G5Member> member = g5MemberRepository.findByMbId(mbId);
        if (member.isPresent()) {
            return modelMapper.map(member.get(), SuperAdminDto.class);
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("mybatisTransactionManager")
    public void create(CreateSuperAdminRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        G5MemberDto superAdmin = modelMapper.map(request, G5MemberDto.class);
        Optional<G5Member> duplicatedWorker = g5MemberRepository.findByMbId(superAdmin.getMbId());
        if (duplicatedWorker.isPresent()) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 아이디입니다.");
        }

        if( StringUtils.isNotBlank(superAdmin.getTelephone())){
            Map<String,Object> condition = new HashMap<String,Object>();
            condition.put("mb_key", superAdmin.getTelephone());
            if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다.");
            }
        }

        superAdmin.setMbLevel(MemberLevel.SUPER_ADMIN.getCode());
        superAdmin.setMbIp(AuthenticationHelper.getRemoteIp());

        if (StringUtils.isNotEmpty(superAdmin.getMbPassword())) {
            superAdmin.setMbPassword(mysqlPasswordEncoder.encode(superAdmin.getMbPassword()));
        }

        superAdmin.setMemberShipNo(mbKeyGenerateService.generateKey());
        g5MemberDao.createSuperAdmin(superAdmin);
    }

    @Transactional("mybatisTransactionManager")
    public void update(UpdateSuperAdminRequest request, String mbId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if (!StringUtils.equals(request.getMbId(), mbId)) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        G5MemberDto superAdmin = modelMapper.map(request, G5MemberDto.class);
        if( StringUtils.isNotBlank(superAdmin.getTelephone())){
            Map<String,Object> condition = new HashMap<String,Object>();
            condition.put("mb_key", superAdmin.getTelephone());
            condition.put("current_mb_id", superAdmin.getMbId());
            if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다.");
            }
        }

        Optional<G5Member> existWorker = g5MemberRepository.findByMbId(superAdmin.getMbId());
        if (existWorker.isPresent()) {
            if (StringUtils.isNotEmpty(superAdmin.getMbPassword())) {
                superAdmin.setMbPassword(mysqlPasswordEncoder.encode(superAdmin.getMbPassword()));
            }
            superAdmin.setMbLevel(MemberLevel.SUPER_ADMIN.getCode());
            g5MemberDao.updateSuperAdmin(superAdmin);
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }


    @Transactional("transactionManager")
    public int delete(String mbId) {
        Optional<G5Member> existWorker = g5MemberRepository.findByMbId(mbId);
        if (existWorker.isPresent()) {
            // TODO point 차감이 필요한가?
            return g5MemberRepository.deleteByMbId(mbId);
        }
        else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("transactionManager")
    public int deleteList(List<String> mbIds) {
        int deleteCnt = 0;
        for (String mbId : mbIds) {
            deleteCnt += g5MemberRepository.deleteByMbId(mbId);
        }
        return deleteCnt;
    }


}
