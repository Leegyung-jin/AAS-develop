package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "authority_level")
@IdClass(AuthorityLevelKey.class)
public class AuthorityLevel {
    // 등급별 권한
    @Id
    @Basic
    @Column(name = "mb_level", columnDefinition = "tinyint")
    private Integer mbLevel;

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
