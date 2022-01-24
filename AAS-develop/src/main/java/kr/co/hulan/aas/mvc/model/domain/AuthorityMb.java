package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "authority_mb")
@IdClass(AuthorityMbKey.class)
public class AuthorityMb {
    @Id
    @Basic
    @Column(name = "mb_id", columnDefinition = "varchar(20)")
    private String mbId;

    @Id
    @Basic
    @Column(name = "authority_id", columnDefinition = "varchar(32)")
    private String authorityId;

    @Basic
    @Column(name = "update_date", columnDefinition="datetime")
    private java.util.Date updateDate;

    @Basic
    @Column(name = "updater", columnDefinition="varchar(20)")
    private String updater;

    @PrePersist
    public void prePersist(){
        if( updateDate == null ){
            updateDate = new java.util.Date();
        }
    }
}
