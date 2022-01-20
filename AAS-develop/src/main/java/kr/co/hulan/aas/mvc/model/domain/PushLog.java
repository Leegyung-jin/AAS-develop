package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "push_log")
public class PushLog {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "pl_idx", columnDefinition="int(11)")
    private Integer plIdx;
    @Basic
    @Column(name = "url", columnDefinition="varchar(255)")
    private String url;
    @Basic
    @Column(name = "token", columnDefinition="varchar(255)")
    private String token;
    @Basic
    @Column(name = "title", columnDefinition="varchar(255)")
    private String title;
    @Basic
    @Column(name = "content", columnDefinition="varchar(255)")
    private String content;
    @Basic
    @Column(name = "sub_data", columnDefinition="varchar(255)")
    private String subData;
    @Basic
    @Column(name = "pl_datetime", columnDefinition="datetime")
    private java.util.Date plDatetime;
    @Basic
    @Column(name = "pl_response", columnDefinition="text")
    private String plResponse;

}
