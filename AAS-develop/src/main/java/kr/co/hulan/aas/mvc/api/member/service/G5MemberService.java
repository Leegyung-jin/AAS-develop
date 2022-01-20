package kr.co.hulan.aas.mvc.api.member.service;

import com.google.common.collect.Lists;
import java.util.Date;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.config.security.oauth.service.SecurityUserService;
import kr.co.hulan.aas.mvc.api.myInfo.controller.request.ChangePasswordRequest;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class G5MemberService {

    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    G5MemberRepository g5MemberRepository;

    @Autowired
    PasswordEncoder mysqlPasswordEncoder;

    @Transactional("transactionManager")
    public void changePassword(long mbNo, String currentPwd, String newPwd){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Optional<G5Member> memberOp =  g5MemberRepository.findById(mbNo);
        if( !memberOp.isPresent() ){
            throw new CommonException(BaseCode.ERR_NOT_EXISTS_USER.code(), BaseCode.ERR_NOT_EXISTS_USER.message());
        }

        G5Member member = memberOp.get();
        if( StringUtils.equals(member.getMbPassword(), mysqlPasswordEncoder.encode(currentPwd)) ){
            member.setMbPassword(mysqlPasswordEncoder.encode(newPwd));
            member.setPwdChangeDate(new Date());
            g5MemberRepository.save(member);
        }
        else {
            throw new CommonException(BaseCode.ERR_INCORRECT_PASSWORD.code(), BaseCode.ERR_INCORRECT_PASSWORD.message());
        }
    }

    @Transactional("transactionManager")
    public void postponeChangingPassword(long mbNo){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Optional<G5Member> memberOp =  g5MemberRepository.findById(mbNo);
        if( !memberOp.isPresent() ){
            throw new CommonException(BaseCode.ERR_NOT_EXISTS_USER.code(), BaseCode.ERR_NOT_EXISTS_USER.message());
        }
        G5Member member = memberOp.get();
        member.setPwdChangeDate(new Date());
        g5MemberRepository.save(member);
    }

    @Transactional("transactionManager")
    public void normalizeUserByMbId(String mbId){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        SecurityUser user = securityUserService.loadUserByUsername(mbId);
        if( user == null ){
            throw new CommonException(BaseCode.ERR_NOT_EXISTS_USER.code(), BaseCode.ERR_NOT_EXISTS_USER.message());
        }
        normalizeUser(user.getMbNo());
    }

    @Transactional("transactionManager")
    public void normalizeUser(long mbNo){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }

        Optional<G5Member> memberOp =  g5MemberRepository.findById(mbNo);
        if( !memberOp.isPresent() ){
            throw new CommonException(BaseCode.ERR_NOT_EXISTS_USER.code(), BaseCode.ERR_NOT_EXISTS_USER.message());
        }
        G5Member member = memberOp.get();
        member.setPwdChangeDate(new Date());
        member.setAttemptLoginCount(0);
        g5MemberRepository.save(member);
    }

}


