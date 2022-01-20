package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@IdClass(G5BoardFileId.class)
@Table(name = "g5_board_file")
public class G5BoardFile implements Serializable {

    @Id
    @Basic
    @Column(name = "bo_table", columnDefinition="varchar(50)")
    private String boTable;

    @Id
    @Basic
    @Column(name = "wr_id", columnDefinition="int(11)")
    private Integer wrId;

    @Id
    @Basic
    @Column(name = "bf_no", columnDefinition="int(11)")
    private Integer bfNo;


    @Basic
    @Column(name = "bf_source", columnDefinition="varchar(180)")
    private String bfSource;
    @Basic
    @Column(name = "bf_file", columnDefinition="varchar(180)")
    private String bfFile;
    @Basic
    @Column(name = "bf_download", columnDefinition="int(11)")
    private Integer bfDownload;
    @Basic
    @Column(name = "bf_content", columnDefinition="text")
    private String bfContent;
    @Basic
    @Column(name = "bf_filesize", columnDefinition="int(11)")
    private Integer bfFilesize;
    @Basic
    @Column(name = "bf_width", columnDefinition="int(11)")
    private Integer bfWidth;
    @Basic
    @Column(name = "bf_height", columnDefinition="smallint(6)")
    private Integer bfHeight;
    @Basic
    @Column(name = "bf_type", columnDefinition="tinyint(4)")
    private Integer bfType;
    @Basic
    @Column(name = "bf_datetime", columnDefinition="datetime")
    private java.util.Date bfDatetime;

}
