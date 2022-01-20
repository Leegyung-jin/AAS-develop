package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "recruit_wish")
public class RecruitWish {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "rw_idx", columnDefinition="int(11)")
    private Integer rwIdx;
    @Basic
    @Column(name = "rc_idx", columnDefinition="int(11)")
    private Integer rcIdx;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(20)")
    private String mbId;
    @Basic
    @Column(name = "rw_datetime", columnDefinition="datetime")
    private java.util.Date rwDatetime;

}
