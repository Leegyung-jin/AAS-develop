package kr.co.hulan.aas.mvc.api.member.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.config.properties.LoginPolicyProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.member.controller.request.*;
import kr.co.hulan.aas.mvc.api.member.dto.CooperativeCompanyDto;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListCooperativeCompanyResponse;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.SuperAdminDto;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.mapper.WorkSectionDao;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.model.domain.ConCompany;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.WorkSection;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.WorkSectionDto;
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
public class CooperativeCompanyService {

    @Autowired
    MbKeyGenerateService mbKeyGenerateService;

    @Autowired
    G5MemberRepository g5MemberRepository;

    @Autowired
    WorkSectionDao workSectionDao;

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

    public ListCooperativeCompanyResponse findListPage(ListCooperativeCompanyRequest request) {
        return new ListCooperativeCompanyResponse(
                findListCountByCondition(request.getConditionMap()),
                findWithdrawListCountByCondition(request.getConditionMap()),
                findBlockListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<CooperativeCompanyDto> findListByCondition(Map<String,Object> conditionMap) {
        return g5MemberDao.findCooperativeCompanyListByCondition(conditionMap)
                .stream()
                .map(g5MemberDto -> {
                    CooperativeCompanyDto dto = modelMapper.map(g5MemberDto, CooperativeCompanyDto.class);
                    dto.setLocked( loginPolicyProperties.isLocked(g5MemberDto) );
                    dto.setPasswordExpired( loginPolicyProperties.isPasswordExpired(g5MemberDto) );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return g5MemberDao.findCooperativeCompanyListCountByCondition(conditionMap);
    }

    private Long findWithdrawListCountByCondition(Map<String,Object> conditionMap) {
        conditionMap.put("withdraw", "withdraw");
        return findListCountByCondition(conditionMap);
    }

    private Long findBlockListCountByCondition(Map<String,Object> conditionMap) {
        conditionMap.put("block", "block");
        return findListCountByCondition(conditionMap);
    }

    public CooperativeCompanyDto findCooperativeCompanyByMbId(String mbId ){
        G5MemberDto memberDto = g5MemberDao.findByMbId(mbId);
        if (memberDto != null) {
            return modelMapper.map(memberDto, CooperativeCompanyDto.class);
        } else {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
    }

    @Transactional("mybatisTransactionManager")
    public CooperativeCompanyDto create(CreateCooperativeCompanyRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        G5MemberDto cooperativeCompany = modelMapper.map(request, G5MemberDto.class);

        Optional<G5Member> duplicatedWorker = g5MemberRepository.findByMbId(cooperativeCompany.getMbId());
        if (duplicatedWorker.isPresent()) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "이미 존재하는 사업자등록번호입니다.");
        }

        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("mb_key", cooperativeCompany.getMbId());
        if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다. 사업자번호를 확인하여 주세요");
        }

        if( StringUtils.isNotBlank(cooperativeCompany.getTelephone())){
            condition.put("mb_key", cooperativeCompany.getTelephone());
            if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다. 전화번호를 확인하여 주세요.");
            }
        }
        cooperativeCompany.setMbLevel(MemberLevel.COOPERATIVE_COMPANY.getCode());
        cooperativeCompany.setMbIp(AuthenticationHelper.getRemoteIp());

        if (StringUtils.isNotEmpty(cooperativeCompany.getMbPassword())) {
            cooperativeCompany.setMbPassword(mysqlPasswordEncoder.encode(cooperativeCompany.getMbPassword()));
        }

        cooperativeCompany.setMemberShipNo(mbKeyGenerateService.generateKey());
        g5MemberDao.createCooperativeCompany(cooperativeCompany);

        CooperativeCompanyDto resultDto = modelMapper.map(cooperativeCompany, CooperativeCompanyDto.class);
        if( StringUtils.isNotBlank(cooperativeCompany.getWorkSectionA())){
            WorkSectionDto sectionDto = workSectionDao.findById(cooperativeCompany.getWorkSectionA());
            if( sectionDto != null  ){
                resultDto.setWorkSectionNameA(sectionDto.getSectionName());
            }
        }
        return resultDto;
    }


    @Transactional("mybatisTransactionManager")
    public void update(UpdateCooperativeCompanyRequest request, String mbId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if (!StringUtils.equals(request.getMbId(), mbId)) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        G5MemberDto cooperativeCompany = modelMapper.map(request, G5MemberDto.class);

        if( StringUtils.isNotBlank(cooperativeCompany.getTelephone())){
            Map<String,Object> condition = new HashMap<String,Object>();
            condition.put("mb_key", cooperativeCompany.getTelephone());
            condition.put("current_mb_id", cooperativeCompany.getMbId());
            if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다.");
            }
        }

        Optional<G5Member> existCooperativeCompany = g5MemberRepository.findByMbId(cooperativeCompany.getMbId());
        if (existCooperativeCompany.isPresent()) {

            if (StringUtils.isNotEmpty(cooperativeCompany.getMbPassword())) {
                cooperativeCompany.setMbPassword(mysqlPasswordEncoder.encode(cooperativeCompany.getMbPassword()));
            }
            g5MemberDao.updateCooperativeCompany(cooperativeCompany);
        }
        else {
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
