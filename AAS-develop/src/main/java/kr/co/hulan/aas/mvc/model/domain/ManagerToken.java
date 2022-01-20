package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "manager_token")
public class ManagerToken {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "mt_idx", columnDefinition="int(11)")
    private Integer mtIdx;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(30)")
    private String mbId;
    @Basic
    @Column(name = "mt_token", columnDefinition="varchar(255)")
    private String mtToken;
    @Basic
    @Column(name = "mt_yn", columnDefinition="tinyint(4)")
    private Integer mtYn;
    @Basic
    @Column(name = "mt_datetime", columnDefinition="datetime")
    private java.util.Date mtDatetime;

}
