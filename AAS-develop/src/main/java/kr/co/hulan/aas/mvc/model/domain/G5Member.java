package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "g5_member")
public class G5Member {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "mb_no", columnDefinition="int(11)")
    private Long mbNo;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(20)")
    private String mbId;
    @Basic
    @Column(name = "mb_password", columnDefinition="varchar(180)")
    private String mbPassword;
    @Basic
    @Column(name = "mb_name", columnDefinition="varchar(180)")
    private String name;
    @Basic
    @Column(name = "mb_nick", columnDefinition="varchar(180)")
    private String mbNick;
    @Basic
    @Column(name = "mb_nick_date", columnDefinition="date")
    private java.util.Date mbNickDate;
    @Basic
    @Column(name = "mb_email", columnDefinition="varchar(180)")
    private String mbEmail;
    @Basic
    @Column(name = "mb_homepage", columnDefinition="varchar(180)")
    private String mbHomepage;
    @Basic
    @Column(name = "mb_level", columnDefinition="tinyint(4)")
    private Integer mbLevel;
    @Basic
    @Column(name = "mb_sex", columnDefinition="char(1)")
    private String mbSex;
    @Basic
    @Column(name = "mb_birth", columnDefinition="varchar(10)")
    private String birthday;
    @Basic
    @Column(name = "mb_tel", columnDefinition="varchar(20)")
    private String telephone;
    @Basic
    @Column(name = "mb_hp", columnDefinition="varchar(20)")
    private String mbHp;
    @Basic
    @Column(name = "mb_certify", columnDefinition="varchar(20)")
    private String mbCertify;
    @Basic
    @Column(name = "mb_adult", columnDefinition="tinyint(4)")
    private Integer mbAdult;
    @Basic
    @Column(name = "mb_dupinfo", columnDefinition="varchar(180)")
    private String mbDupinfo;
    @Basic
    @Column(name = "mb_zip1", columnDefinition="char(3)")
    private String mbZip1;
    @Basic
    @Column(name = "mb_zip2", columnDefinition="char(3)")
    private String mbZip2;
    @Basic
    @Column(name = "mb_addr1", columnDefinition="varchar(180)")
    private String mbAddr1;
    @Basic
    @Column(name = "mb_addr2", columnDefinition="varchar(180)")
    private String mbAddr2;
    @Basic
    @Column(name = "mb_addr3", columnDefinition="varchar(180)")
    private String mbAddr3;
    @Basic
    @Column(name = "mb_addr_jibeon", columnDefinition="varchar(180)")
    private String mbAddrJibeon;
    @Basic
    @Column(name = "mb_signature", columnDefinition="text")
    private String mbSignature;
    @Basic
    @Column(name = "mb_recommend", columnDefinition="varchar(180)")
    private String mbRecommend;
    @Basic
    @Column(name = "mb_point", columnDefinition="int(11)")
    private Long mbPoint;
    @Basic
    @Column(name = "mb_today_login", columnDefinition="datetime")
    private java.util.Date latestLogin;
    @Basic
    @Column(name = "mb_login_ip", columnDefinition="varchar(180)")
    private String mbLoginIp;
    @Basic
    @Column(name = "mb_datetime", columnDefinition="datetime")
    private java.util.Date registDate;
    @Basic
    @Column(name = "mb_ip", columnDefinition="varchar(180)")
    private String mbIp;
    @Basic
    @Column(name = "mb_leave_date", columnDefinition="varchar(8)")
    private String withdrawDate;
    @Basic
    @Column(name = "mb_intercept_date", columnDefinition="varchar(8)")
    private String blockDate;
    @Basic
    @Column(name = "mb_email_certify", columnDefinition="datetime")
    private java.util.Date mbEmailCertify;
    @Basic
    @Column(name = "mb_email_certify2", columnDefinition="varchar(180)")
    private String mbEmailCertify2;
    @Basic
    @Column(name = "mb_memo", columnDefinition="text")
    private String mbMemo;
    @Basic
    @Column(name = "mb_lost_certify", columnDefinition="varchar(180)")
    private String mbLostCertify;
    @Basic
    @Column(name = "mb_mailling", columnDefinition="tinyint(4)")
    private Integer mbMailling;
    @Basic
    @Column(name = "mb_sms", columnDefinition="tinyint(4)")
    private Integer mbSms;
    @Basic
    @Column(name = "mb_open", columnDefinition="tinyint(4)")
    private Integer mbOpen;
    @Basic
    @Column(name = "mb_open_date", columnDefinition="date")
    private java.util.Date mbOpenDate;
    @Basic
    @Column(name = "mb_profile", columnDefinition="text")
    private String mbProfile;
    @Basic
    @Column(name = "mb_memo_call", columnDefinition="varchar(180)")
    private String mbMemoCall;
    @Basic
    @Column(name = "mb_1", columnDefinition="varchar(180)")
    private String memberShipNo;
    @Basic
    @Column(name = "mb_2", columnDefinition="varchar(180)")
    private String workPlace;
    @Basic
    @Column(name = "mb_3", columnDefinition="varchar(180)")
    private String mb3;
    @Basic
    @Column(name = "mb_4", columnDefinition="varchar(180)")
    private String mb4;

    @Basic
    @Column(name = "mb_5", columnDefinition="varchar(180)")
    private String bloodType;
    @Basic
    @Column(name = "mb_6", columnDefinition="varchar(180)")
    private String mb6;
    @Basic
    @Column(name = "mb_7", columnDefinition="varchar(180)")
    private String mb7;
    @Basic
    @Column(name = "mb_8", columnDefinition="varchar(180)")
    private String mb8;
    @Basic
    @Column(name = "mb_9", columnDefinition="varchar(180)")
    private String mb9;
    @Basic
    @Column(name = "mb_10", columnDefinition="varchar(180)")
    private String safetyEducationFile;
    @Basic
    @Column(name = "mb_11", columnDefinition="varchar(180)")
    private String mb11;

    @Basic
    @Column(name = "mb_12", columnDefinition="varchar(180)")
    private String mb12;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "mb_12", nullable = true)
    private ConCompany conCompany;
     */

    @Basic
    @Column(name = "mb_13", columnDefinition="varchar(180)")
    private String mb13;
    @Basic
    @Column(name = "mb_14", columnDefinition="varchar(180)")
    private String mb14;
    @Basic
    @Column(name = "mb_15", columnDefinition="varchar(180)")
    private String mb15;
    @Basic
    @Column(name = "mb_16", columnDefinition="varchar(180)")
    private String mb16;
    @Basic
    @Column(name = "mb_17", columnDefinition="varchar(180)")
    private String mb17;
    @Basic
    @Column(name = "mb_18", columnDefinition="varchar(180)")
    private String pushNormal;
    @Basic
    @Column(name = "mb_19", columnDefinition="varchar(180)")
    private String pushDanger;
    @Basic
    @Column(name = "mb_20", columnDefinition="varchar(180)")
    private String deviceId;

    @Basic
    @Column(name = "app_version", columnDefinition="varchar(6)")
    private String appVersion;

    @Basic
    @Column(name = "work_section_a", columnDefinition="varchar(8)")
    private String workSectionA;

    @Basic
    @Column(name = "pwd_change_date", columnDefinition="datetime")
    private Date pwdChangeDate;

    @Basic
    @Column(name = "attempt_login_count", columnDefinition="integer")
    private Integer attemptLoginCount;

    @Basic
    @Column(name = "cc_id", columnDefinition="varchar(50)")
    private String ccId;

    @Basic
    @Column(name = "office_no", columnDefinition="bigint(20)")
    private Long officeNo;

    /**
     * insert 되기전 (persist 되기전) 실행된다.
     * */
    @PrePersist
    public void prePersist() {
        if(StringUtils.isBlank(workSectionA)){
            workSectionA = null;
        }
        if( StringUtils.isBlank(mbNick)){
            mbNick = name;
        }
        if( StringUtils.isBlank(mbEmail)){
            mbEmail = "";
        }
        if( StringUtils.isBlank(mbHomepage)){
            mbHomepage = "";
        }
        if( StringUtils.isBlank(mbSex)){
            mbSex = "";
        }
        if( StringUtils.isBlank(birthday)){
            birthday = "";
        }
        if( StringUtils.isBlank(birthday)){
            birthday = "";
        }
        if( StringUtils.isBlank(mbHp)){
            mbHp = "";
        }
        if( StringUtils.isBlank(mbCertify)){
            mbCertify = "";
        }
        if( mbAdult == null){
            mbAdult = 0;
        }
        if( StringUtils.isBlank(mbDupinfo)){
            mbDupinfo = "";
        }
        if( StringUtils.isBlank(mbDupinfo)){
            mbDupinfo = "";
        }
        if( StringUtils.isBlank(mbDupinfo)){
            mbDupinfo = "";
        }
        if( StringUtils.isBlank(mbZip1)){
            mbZip1 = "";
        }
        if( StringUtils.isBlank(mbZip2)){
            mbZip2 = "";
        }
        if( StringUtils.isBlank(mbAddr1)){
            mbAddr1 = "";
        }
        if( StringUtils.isBlank(mbAddr2)){
            mbAddr2 = "";
        }
        if( StringUtils.isBlank(mbAddr3)){
            mbAddr3 = "";
        }
        if( StringUtils.isBlank(mbAddrJibeon)){
            mbAddrJibeon = "";
        }
        if( StringUtils.isBlank(mbRecommend)){
            mbRecommend = "";
        }
        if( mbPoint == null){
            mbPoint = 0L;
        }
        if( StringUtils.isBlank(mbLoginIp)){
            mbLoginIp = "";
        }
        if( StringUtils.isBlank(mbIp)){
            mbIp = "";
        }
        if( StringUtils.isBlank(withdrawDate)){
            withdrawDate = "";
        }
        if( StringUtils.isBlank(blockDate)){
            blockDate = "";
        }
        if( StringUtils.isBlank(mbEmailCertify2)){
            mbEmailCertify2 = "";
        }
        if( mbMailling == null){
            mbMailling = 0;
        }
        if( mbSms == null){
            mbSms = 0;
        }
        if( mbOpen == null){
            mbOpen = 0;
        }
        if( StringUtils.isBlank(mbMemoCall)){
            mbMemoCall = "";
        }
        if( StringUtils.isBlank(memberShipNo)){
            memberShipNo = "";
        }
        if( StringUtils.isBlank(workPlace)){
            workPlace = "";
        }
        if( StringUtils.isBlank(bloodType)){
            bloodType = "";
        }
        if( StringUtils.isBlank(safetyEducationFile)){
            safetyEducationFile = "";
        }
        if( StringUtils.isBlank(pushNormal)){
            pushNormal = "";
        }
        if( StringUtils.isBlank(pushDanger)){
            pushDanger = "";
        }
        if( StringUtils.isBlank(deviceId)){
            deviceId = "";
        }
        if( StringUtils.isBlank(mb3)){
            mb3 = "";
        }
        if( StringUtils.isBlank(mb4)){
            mb4 = "";
        }
        if( StringUtils.isBlank(mb6)){
            mb6 = "";
        }
        if( StringUtils.isBlank(mb7)){
            mb7 = "";
        }
        if( StringUtils.isBlank(mb8)){
            mb8 = "";
        }
        if( StringUtils.isBlank(mb9)){
            mb9 = "";
        }
        if( StringUtils.isBlank(mb11)){
            mb11 = "";
        }
        if( StringUtils.isBlank(mb12)){
            mb12 = "";
        }
        if( StringUtils.isBlank(mb13)){
            mb13 = "";
        }
        if( StringUtils.isBlank(mb14)){
            mb14 = "";
        }
        if( StringUtils.isBlank(mb15)){
            mb15 = "";
        }
        if( StringUtils.isBlank(mb16)){
            mb16 = "";
        }
        if( StringUtils.isBlank(mb17)){
            mb17 = "";
        }
        if(pwdChangeDate == null ){
            pwdChangeDate = new Date();
        }
        if(attemptLoginCount == null ){
            attemptLoginCount = 0;
        }
    }

    @PreUpdate
    public void preUpdate(){
        prePersist();
    }
}
