package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.api.authority.model.dto.AuthorityUserDto;
import kr.co.hulan.aas.mvc.api.member.dto.ConCompanyManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.ConstructionSiteManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.FieldManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeGroupManagerDto;
import kr.co.hulan.aas.mvc.api.member.dto.OfficeManagerDto;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.MemberOtpPhoneDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface G5MemberDao {
    List<G5MemberDto> findListByCondition(Map<String,Object> condition);
    Long findListCountByCondition(Map<String,Object> condition);
    List<G5MemberDto> findCooperativeCompanyCodeList(Map<String,Object> condition);

    List<G5MemberDto> findCooperativeCompanyListByCondition(Map<String,Object> condition);
    Long findCooperativeCompanyListCountByCondition(Map<String,Object> condition);

    List<G5MemberDto> findFieldManagerListByCondition(Map<String,Object> condition);
    Long findFieldManagerListCountByCondition(Map<String,Object> condition);

    List<G5MemberDto> findSuperAdminListByCondition(Map<String,Object> condition);
    Long findSuperAdminListCountByCondition(Map<String,Object> condition);

    List<G5MemberDto> findWorkplaceWorkerListForNoticeByCondition(Map<String,Object> condition);

    G5MemberDto findByMbId(String mbId);

    Long findDuplicatedMemberCount(Map<String,Object> condition);

    int createWorker(G5MemberDto g5MemberDto);
    int updateWorker(G5MemberDto g5MemberDto);

    int createFieldManager(G5MemberDto g5MemberDto);
    int updateFieldManager(G5MemberDto g5MemberDto);

    int createCooperativeCompany(G5MemberDto g5MemberDto);
    int updateCooperativeCompany(G5MemberDto g5MemberDto);

    int createSuperAdmin(G5MemberDto g5MemberDto);
    int updateSuperAdmin(G5MemberDto g5MemberDto);

    int updateLoginSuccess(G5MemberDto g5MemberDto);
    int updateLoginFail(G5MemberDto g5MemberDto);

    void createOrUpdateOtpPhone(MemberOtpPhoneDto phone);
    void deleteAllOtpPhone(String mbId);

    /******************************************
    ** 발주사 관리자
    *******************************************/
    List<OfficeManagerDto> findOfficeManagerListByCondition(Map<String,Object> condition);
    Long countOfficeManagerListByCondition(Map<String,Object> condition);

    OfficeManagerDto findOfficeManagerInfo(String mbId);

    /******************************************
     ** 발주사 그룹 관리자
     *******************************************/
    List<OfficeGroupManagerDto> findOfficeGroupManagerListByCondition(Map<String,Object> condition);
    Long countOfficeGroupManagerListByCondition(Map<String,Object> condition);

    OfficeGroupManagerDto findOfficeGroupManagerInfo(String mbId);


    /******************************************
     ** 건설사 관리자
     *******************************************/
    List<ConCompanyManagerDto> findConCompanyManagerListByCondition(Map<String,Object> condition);
    Long countConCompanyManagerListByCondition(Map<String,Object> condition);

    ConCompanyManagerDto findConCompanyManagerInfo(String mbId);

    /******************************************
     ** 건설 현장관리자
     *******************************************/
    List<ConstructionSiteManagerDto> findConstructionSiteManagerListByCondition(Map<String,Object> condition);
    Long countConstructionSiteManagerListByCondition(Map<String,Object> condition);

    /******************************************
     ** 권한 등록 가능한 사용자
     *******************************************/
    List<AuthorityUserDto> findAuthorityInsertUserListByCondition(Map<String,Object> condition);
    Integer countfindAuthorityInsertUserListByCondition(Map<String, Object> addAdditionalConditionByLevel);

}
