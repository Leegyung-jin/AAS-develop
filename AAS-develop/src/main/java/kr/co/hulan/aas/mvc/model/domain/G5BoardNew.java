package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "g5_board_new")
public class G5BoardNew {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "bn_id", columnDefinition="int(11)")
    private Integer bnId;
    @Basic
    @Column(name = "bo_table", columnDefinition="varchar(50)")
    private String boTable;
    @Basic
    @Column(name = "wr_id", columnDefinition="int(11)")
    private Integer wrId;
    @Basic
    @Column(name = "wr_parent", columnDefinition="int(11)")
    private Integer wrParent;
    @Basic
    @Column(name = "bn_datetime", columnDefinition="datetime")
    private java.util.Date bnDatetime;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(20)")
    private String mbId;

}
