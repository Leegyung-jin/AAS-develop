package kr.co.hulan.aas.mvc.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "member_level")
public class Level {

    @Id
    @Basic
    @Column(name = "mb_level", columnDefinition="tinyint")
    private Integer mbLevel;
    @Basic
    @Column(name = "mb_level_name", columnDefinition="varchar(32)")
    private String mbLevelName;
    @Basic
    @Column(name = "description", columnDefinition="varchar(1024)")
    private String description;
    @Basic
    @Column(name = "create_date", columnDefinition="datetime")
    private java.util.Date createDate;
    @Basic
    @Column(name = "creator", columnDefinition="varchar(20)")
    private String creator;
    @Basic
    @Column(name = "update_date", columnDefinition="datetime")
    private java.util.Date updateDate;
    @Basic
    @Column(name = "updater", columnDefinition="varchar(20)")
    private String updater;


    @PrePersist
    public void prePersist(){
        if( createDate == null ){
            createDate = new java.util.Date();
        }
        if( updateDate == null ){
            updateDate = new java.util.Date();
        }
    }
}
