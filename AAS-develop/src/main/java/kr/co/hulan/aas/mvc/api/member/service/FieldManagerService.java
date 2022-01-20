package kr.co.hulan.aas.mvc.api.member.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.common.utils.PhoneNoUtils;
import kr.co.hulan.aas.config.properties.LoginPolicyProperties;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.api.member.controller.request.*;
import kr.co.hulan.aas.mvc.api.member.dto.CooperativeCompanyDto;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.controller.response.ListFieldManagerResponse;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.mapper.HulanSequenceDao;
import kr.co.hulan.aas.mvc.dao.repository.ConCompanyRepository;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.model.domain.ConCompany;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.dto.ConCompanyDto;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.HulanSequenceDto;
import kr.co.hulan.aas.mvc.model.dto.MemberOtpPhoneDto;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FieldManagerService {

    @Autowired
    MbKeyGenerateService mbKeyGenerateService;

    @Autowired
    G5MemberRepository g5MemberRepository;

    @Autowired
    ConCompanyRepository conCompanyRepository;

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

    public ListFieldManagerResponse findListPage(ListFieldManagerRequest request) {
        return new ListFieldManagerResponse(
                findListCountByCondition(request.getConditionMap()),
                findListByCondition(request.getConditionMap())
        );
    }

    public List<FieldManagerDto> findListByCondition(Map<String,Object> conditionMap) {
        return g5MemberDao.findFieldManagerListByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap))
                .stream()
                .map(g5MemberDto -> {
                    FieldManagerDto dto = modelMapper.map(g5MemberDto, FieldManagerDto.class);
                    dto.setLocked( loginPolicyProperties.isLocked(g5MemberDto) );
                    dto.setPasswordExpired( loginPolicyProperties.isPasswordExpired(g5MemberDto) );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private Long findListCountByCondition(Map<String,Object> conditionMap) {
        return g5MemberDao.findFieldManagerListCountByCondition(AuthenticationHelper.addAdditionalConditionByLevel(conditionMap));
    }

    public FieldManagerDto findFieldManagerByMbId(String mbId) {
        G5MemberDto memberDto = g5MemberDao.findByMbId(mbId);
        if (memberDto == null) {
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), BaseCode.ERR_DETAIL_EXCEPTION.message());
        }
        return modelMapper.map(memberDto, FieldManagerDto.class);
    }

    @Transactional("mybatisTransactionManager")
    public FieldManagerDto create(CreateFieldManagerRequest request) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        G5MemberDto fieldManager = modelMapper.map(request, G5MemberDto.class);

        Optional<ConCompany> conCompany = conCompanyRepository.findById(request.getCcId());
        if( conCompany.isPresent() ){
            fieldManager.setMb12(conCompany.get().getCcId());
            fieldManager.setMb13(conCompany.get().getCcName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 건설사입니다.");
        }

        if( StringUtils.isNotBlank(fieldManager.getTelephone())){
            Map<String,Object> condition = new HashMap<String,Object>();
            condition.put("mb_key", fieldManager.getTelephone());
            if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다. 전화번호를 확인하여 주세요.");
            }
        }

        fieldManager.setMbLevel(MemberLevel.FIELD_MANAGER.getCode());
        fieldManager.setMbIp(AuthenticationHelper.getRemoteIp());

        if (StringUtils.isNotEmpty(fieldManager.getMbPassword())) {
            fieldManager.setMbPassword(mysqlPasswordEncoder.encode(fieldManager.getMbPassword()));
        }

        fieldManager.setMbId(mbKeyGenerateService.generateKey());
        g5MemberDao.createFieldManager(fieldManager);

        List<MemberOtpPhoneDto> otpPhoneList = request.getOtpPhoneList();

        if( otpPhoneList != null && otpPhoneList.size() != 0 ){
            for( MemberOtpPhoneDto otpPhone : otpPhoneList ){
                if( StringUtils.isNotBlank(otpPhone.getPhoneNo())){
                    if( PhoneNoUtils.isValidPhoneNo(otpPhone.getPhoneNo()) ){
                        g5MemberDao.createOrUpdateOtpPhone(MemberOtpPhoneDto.builder()
                            .mbId(fieldManager.getMbId())
                            .phoneNo(StringUtils.trim(otpPhone.getPhoneNo()))
                            .createDate(new Date())
                            .creator(loginUser.getMbId())
                            .build());
                    }
                    else {
                        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "OTP 수신단말번호["+otpPhone.getPhoneNo()+"]가 올바르지 않습니다.");
                    }
                }
            }
        }
        return modelMapper.map(fieldManager, FieldManagerDto.class);
    }


    @Transactional("mybatisTransactionManager")
    public void update(UpdateFieldManagerRequest request, String mbId) {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        if (!StringUtils.equals(request.getMbId(), mbId)) {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "아이디가 일치하지 않습니다.");
        }

        G5MemberDto fieldManager = modelMapper.map(request, G5MemberDto.class);

        Optional<ConCompany> conCompany = conCompanyRepository.findById(request.getCcId());
        if( conCompany.isPresent() ){
            fieldManager.setMb12(conCompany.get().getCcId());
            fieldManager.setMb13(conCompany.get().getCcName());
        }
        else {
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "존재하지 않는 건설사입니다.");
        }

        if( StringUtils.isNotBlank(fieldManager.getTelephone())){
            Map<String,Object> condition = new HashMap<String,Object>();
            condition.put("mb_key", fieldManager.getTelephone());
            condition.put("current_mb_id", fieldManager.getMbId());
            if (g5MemberDao.findDuplicatedMemberCount(condition) > 0) {
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "휴대전화번호(사업자등록번호), 연락처는 하나라도 중복등록이 불가능합니다.");
            }
        }

        Optional<G5Member> existWorker = g5MemberRepository.findByMbId(fieldManager.getMbId());
        if (existWorker.isPresent()) {
            if (StringUtils.isNotEmpty(fieldManager.getMbPassword())) {
                fieldManager.setMbPassword(mysqlPasswordEncoder.encode(fieldManager.getMbPassword()));
            }
            g5MemberDao.updateFieldManager(fieldManager);

            g5MemberDao.deleteAllOtpPhone(fieldManager.getMbId());
            List<MemberOtpPhoneDto> otpPhoneList = request.getOtpPhoneList();
            if( otpPhoneList != null && otpPhoneList.size() != 0 ){
                for( MemberOtpPhoneDto otpPhone : otpPhoneList ){
                    if( StringUtils.isNotBlank(otpPhone.getPhoneNo())){

                        if( PhoneNoUtils.isValidPhoneNo(otpPhone.getPhoneNo()) ){
                            g5MemberDao.createOrUpdateOtpPhone(MemberOtpPhoneDto.builder()
                                .mbId(fieldManager.getMbId())
                                .phoneNo(StringUtils.trim(otpPhone.getPhoneNo()))
                                .createDate(new Date())
                                .creator(loginUser.getMbId())
                                .build());
                        }
                        else {
                            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "OTP 수신단말번호["+otpPhone.getPhoneNo()+"]가 올바르지 않습니다.");
                        }
                    }
                }
            }
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
    public int deleteFieldManagers(List<String> mbIds) {
        int deleteCnt = 0;
        for (String mbId : mbIds) {
            deleteCnt += g5MemberRepository.deleteByMbId(mbId);
        }
        return deleteCnt;
    }
}
