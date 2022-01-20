package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_section")
public class WorkSection {

    @Id
    @Basic
    @Column(name = "section_cd", columnDefinition="varchar(8)")
    private String sectionCd;
    @Basic
    @Column(name = "section_name", columnDefinition="varchar(64)")
    private String sectionName;
    @Basic
    @Column(name = "description", columnDefinition="varchar(256)")
    private String description;
    @Basic
    @Column(name = "parent_section_cd", columnDefinition="varchar(8)")
    private String parentSectionCd;
    @Basic
    @Column(name = "create_date", columnDefinition="datetime")
    private Date createDate;
    @Basic
    @Column(name = "creator", columnDefinition="varchar(20)")
    private String creator;
    @Basic
    @Column(name = "update_date", columnDefinition="datetime")
    private Date updateDate;
    @Basic
    @Column(name = "updater", columnDefinition="varchar(20)")
    private String updater;

    @PrePersist
    public void prePersist() {
        if(StringUtils.isEmpty(parentSectionCd)){
            parentSectionCd = null;
        }
        if(createDate == null ){
            createDate = new Date();
        }
        if(updateDate == null ){
            updateDate = new Date();
        }
    }
}
