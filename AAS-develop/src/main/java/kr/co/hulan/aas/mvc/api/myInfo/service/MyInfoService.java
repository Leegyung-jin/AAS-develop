package kr.co.hulan.aas.mvc.api.myInfo.service;

import java.util.Date;
import java.util.Optional;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.config.security.oauth.model.SecurityMemberResponse;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.config.security.oauth.service.DetailSecurityUserService;
import kr.co.hulan.aas.mvc.api.member.service.G5MemberService;
import kr.co.hulan.aas.mvc.api.myInfo.controller.request.ChangePasswordRequest;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyInfoService {

    @Autowired
    private DetailSecurityUserService detailSecurityUserService;

    @Autowired
    private G5MemberService g5MemberService;

    public SecurityMemberResponse getDetail() {
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        return detailSecurityUserService.getDetailSecurityUser(loginUser.getUsername());
    }


    @Transactional("transactionManager")
    public void changePassword(ChangePasswordRequest changeRequest){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        g5MemberService.changePassword(loginUser.getMbNo(), changeRequest.getCurrentPwd(), changeRequest.getNewPwd());
    }

    @Transactional("transactionManager")
    public void postponeChangingPassword(){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser == null) {
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        g5MemberService.postponeChangingPassword(loginUser.getMbNo());
    }
}
