package kr.co.hulan.aas.common.utils;

import java.util.HashMap;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.security.Principal;
import java.util.Map;

public class AuthenticationHelper {

    public static SecurityUser getSecurityUser(Principal principal){
        if (principal != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = oAuth2Authentication.getUserAuthentication();
            return (SecurityUser) authentication.getPrincipal();
        }
        return null;
    }

    public static SecurityUser getSecurityUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( principal != null && principal instanceof SecurityUser ){
            return (SecurityUser)principal;
        }
        return null;
    }

    public static SecurityUser getSecurityUser(boolean mustExists){
        SecurityUser user = getSecurityUser();
        if( user == null && mustExists ){
            throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
        }
        return user;
    }

    public static OAuth2AuthenticationDetails getAuthenticationDetail(){
        Object detail = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if( detail != null && detail instanceof OAuth2AuthenticationDetails){
            return (OAuth2AuthenticationDetails) detail;
        }
        return null;
    }

    public static String getRemoteIp(){
        OAuth2AuthenticationDetails authDetail = getAuthenticationDetail();
        return authDetail != null ?  authDetail.getRemoteAddress() : "";
    }

    public static String getToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if( auth != null && auth instanceof OAuth2Authentication ){
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) auth;
            if( oAuth2Authentication.getDetails() != null && oAuth2Authentication.getDetails() instanceof OAuth2AuthenticationDetails){
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
                return details.getTokenValue();
            }
        }
        return "";
    }

    public static Map<String,Object> addAdditionalConditionByLevel(){
        return addAdditionalConditionByLevel(new HashMap<String,Object>());
    }

    public static Map<String,Object> addAdditionalConditionByLevel( Map<String,Object> condition ){
        SecurityUser loginUser = AuthenticationHelper.getSecurityUser();
        if (loginUser != null ) {
            condition.put("loginUserLevel", loginUser.getMbLevel());
            condition.put("loginUserId", loginUser.getMbId());
            if ( loginUser.getMbLevel() == MemberLevel.OFFICE_MANAGER.getCode()
                || loginUser.getMbLevel() == MemberLevel.WP_GROUP_MANAGER.getCode()
            ) {
                condition.put("loginOfficeNo", loginUser.getLoginOfficeNo() != null ? loginUser.getLoginOfficeNo() : -1);
            }
            if ( loginUser.getMbLevel() == MemberLevel.CONSTRUNCTION_COMPANY_MANAGER.getCode()) {
                condition.put("loginCcId", StringUtils.defaultIfBlank(loginUser.getLoginCcId(), "UNKNOWN") );
            }

            if ( loginUser.getMbLevel() == MemberLevel.COOPERATIVE_COMPANY.getCode()) {
                condition.put("coopMbId", loginUser.getUsername());
                condition.put("loginCoopMbId", loginUser.getUsername());
            }
            if ( loginUser.getMbLevel() == MemberLevel.FIELD_MANAGER.getCode()) {
                condition.put("loginCcId", StringUtils.defaultIfBlank(loginUser.getLoginCcId(), "UNKNOWN") );
                /*
                condition.put("wpId", StringUtils.defaultIfEmpty(loginUser.getWpId(), "UNKNOWN"));
                if( StringUtils.isNotBlank(loginUser.getWpId())){
                    condition.put("loginWpId", loginUser.getWpId());
                }
                 */
            }
        }
        return condition;
    }
}
