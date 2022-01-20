package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "hulan_sequence")
public class HulanSequence {

    @Id
    @Basic
    @Column(name = "seq_name", columnDefinition="varchar(8)")
    private String seqName;
    @Basic
    @Column(name = "next_val", columnDefinition="bigint(20)")
    private Integer nextVal;
    @Basic
    @Column(name = "etc_1", columnDefinition="varchar(16)")
    private String etc1;

}
