package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "con_company")
public class ConCompany {

    @Id
    @Basic
    @Column(name = "cc_id", columnDefinition="varchar(50)")
    private String ccId;
    @Basic
    @Column(name = "cc_name", columnDefinition="varchar(50)")
    private String ccName;
    @Basic
    @Column(name = "cc_num", columnDefinition="varchar(20)")
    private String ccNum;
    @Basic
    @Column(name = "cc_zip1", columnDefinition="varchar(3)")
    private String ccZip1;
    @Basic
    @Column(name = "cc_zip2", columnDefinition="varchar(3)")
    private String ccZip2;
    @Basic
    @Column(name = "cc_addr1", columnDefinition="varchar(255)")
    private String ccAddr1;
    @Basic
    @Column(name = "cc_addr2", columnDefinition="varchar(255)")
    private String ccAddr2;
    @Basic
    @Column(name = "cc_addr3", columnDefinition="varchar(255)")
    private String ccAddr3;
    @Basic
    @Column(name = "cc_addr_jibeon", columnDefinition="varchar(1)")
    private String ccAddrJibeon;
    @Basic
    @Column(name = "cc_tel", columnDefinition="varchar(20)")
    private String ccTel;
    @Basic
    @Column(name = "cc_memo", columnDefinition="text")
    private String ccMemo;


    @Basic
    @Column(name = "hicc_no", columnDefinition = "bigint")
    private Long hiccNo;

    @Basic
    @Column(name = "cc_datetime", columnDefinition="datetime")
    private Date ccDatetime;



}
