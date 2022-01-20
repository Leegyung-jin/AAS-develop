package kr.co.hulan.aas.mvc.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "coop_worker")
public class CoopWorker {

    @Id
    @Basic
    @Column(name = "worker_mb_id", columnDefinition="varchar(20)")
    private String workerMbId;

    @Basic
    @Column(name = "coop_mb_id", columnDefinition="varchar(20)")
    private String coopMbId;

    @Basic
    @Column(name = "create_datetime", columnDefinition="timestamp")
    private Date createDatetime;

    @Basic
    @Column(name = "update_datetime", columnDefinition="timestamp")
    private Date updateDatetime;

    @PrePersist
    public void prePersist(){
        if( createDatetime == null ){
            createDatetime = new Date();
        }
        if( updateDatetime == null ){
            updateDatetime = new Date();
        }
    }
}
