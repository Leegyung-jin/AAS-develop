package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "recruit")
public class Recruit {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "rc_idx", columnDefinition="int(11)")
    private Integer rcIdx;
    @Basic
    @Column(name = "rc_title", columnDefinition="varchar(100)")
    private String rcTitle;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "wp_name", columnDefinition="varchar(100)")
    private String wpName;
    @Basic
    @Column(name = "wpc_id", columnDefinition="varchar(50)")
    private String wpcId;
    @Basic
    @Column(name = "coop_mb_id", columnDefinition="varchar(20)")
    private String coopMbId;
    @Basic
    @Column(name = "coop_mb_name", columnDefinition="varchar(50)")
    private String coopMbName;
    @Basic
    @Column(name = "rc_work1", columnDefinition="varchar(50)")
    private String rcWork1;
    @Basic
    @Column(name = "rc_work2", columnDefinition="varchar(50)")
    private String rcWork2;
    @Basic
    @Column(name = "rc_work3", columnDefinition="varchar(50)")
    private String rcWork3;
    @Basic
    @Column(name = "work_section_a", columnDefinition="varchar(8)")
    private String workSectionA;
    @Basic
    @Column(name = "work_section_b", columnDefinition="varchar(8)")
    private String workSectionB;
    @Basic
    @Column(name = "rc_pay_amount", columnDefinition="varchar(10)")
    private String rcPayAmount;
    @Basic
    @Column(name = "rc_pay_unit", columnDefinition="varchar(5)")
    private String rcPayUnit;
    @Basic
    @Column(name = "rc_tel", columnDefinition="varchar(20)")
    private String rcTel;
    @Basic
    @Column(name = "rc_content", columnDefinition="text")
    private String rcContent;
    @Basic
    @Column(name = "rc_datetime", columnDefinition="datetime")
    private java.util.Date rcDatetime;
    @Basic
    @Column(name = "rc_updatetime", columnDefinition="datetime")
    private java.util.Date rcUpdatetime;

}
