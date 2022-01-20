package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.WorkerWorkPrintStatus;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.PhpSerializeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Data
@ApiModel(value="WorkerWorkPrintDto", description="출력일보 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkerWorkPrintDto {

    private Integer wwpIdx;
    private String ccId = "";
    private String ccName = "";
    private String wpId = "";
    private String wpName = "";
    private String coopMbId = "";
    private String coopMbName = "";
    private String wwpData = "";
    @ApiModelProperty(notes = "상태코드")
    private Integer wwpStatus = WorkerWorkPrintStatus.NOT_SUBMITTED.getCode();
    private java.util.Date wwpDate;
    @ApiModelProperty(notes = "직종", required = true)
    private java.util.Date wwpUpdatetime;


    // Derived
    @ApiModelProperty(notes = "직종", required = true)
    private List<String> wwpJob;

    @ApiModelProperty(notes = "성명", required = true)
    private List<String> workerMbName;

    @ApiModelProperty(notes = "작업 내용", required = true)
    private List<String> wwpWork;

    @ApiModelProperty(notes = "비고", required = true)
    private List<String> wwpMemo;

    @ApiModelProperty(notes = "상태명")
    public String getWwpStatusName(){
        if( wwpStatus != null ){
            WorkerWorkPrintStatus status = WorkerWorkPrintStatus.get(wwpStatus);
            if( status != null ){
                return status.getName();
            }
        }
        return "";
    }

    @JsonIgnore
    public void parseWwpData(){
        if(StringUtils.isNotBlank(wwpData)){
            try{
                Map<Object,Object> map = PhpSerializeUtils.deserialize(wwpData);
                if( map.get("wwp_job") != null ){
                    wwpJob = Arrays.asList(((LinkedHashMap<Object, Object>) map.get("wwp_job")).values().toArray(new String[0]));
                }
                if( map.get("worker_mb_name") != null ){
                    workerMbName = Arrays.asList(((LinkedHashMap<Object, Object>) map.get("worker_mb_name")).values().toArray(new String[0]));
                }
                if( map.get("wwp_work") != null ){
                    wwpWork = Arrays.asList(((LinkedHashMap<Object, Object>) map.get("wwp_work")).values().toArray(new String[0]));
                }
                if( map.get("wwp_memo") != null ){
                    wwpMemo = Arrays.asList(((LinkedHashMap<Object, Object>) map.get("wwp_memo")).values().toArray(new String[0]));
                }
            }
            catch(Exception e){
                e.printStackTrace();
                throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "Json Parsing Error");
            }
        }
    }

    @JsonIgnore
    public void makeWwpData(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("wp_Id", wpId);
        map.put("coop_mb_id", coopMbId);
        map.put("wwp_date", new SimpleDateFormat("yyyy-MM-dd").format(wwpDate));

        if( wwpJob != null ){
            map.put("wwp_job", wwpJob);
        }
        if( workerMbName != null ){
            map.put("worker_mb_name", workerMbName);
        }
        if( wwpWork != null ){
            map.put("wwp_work", wwpWork);
        }
        if( wwpMemo != null ){
            map.put("wwp_memo", wwpMemo);
        }
        try{
            wwpData = PhpSerializeUtils.serialize(map);
            // wwpData = JsonUtil.toStringJson(map);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "Json Parsing Error");
        }
    }

    public static void main(String args[]){
        Integer inter1 = new Integer(193);
        Integer inter2 = new Integer(193);
        System.out.println(inter1 == inter2);
        System.out.println(inter1 != inter2);
    }

}
