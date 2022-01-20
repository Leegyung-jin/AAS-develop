package kr.co.hulan.aas.mvc.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class G5BoardFileId implements Serializable {

    private String boTable;
    private Integer wrId;
    private Integer bfNo;

}
