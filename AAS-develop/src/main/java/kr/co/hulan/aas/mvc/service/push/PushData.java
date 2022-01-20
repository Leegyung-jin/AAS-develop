package kr.co.hulan.aas.mvc.service.push;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.hulan.aas.common.utils.JsonUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PushData {


    private List<String> token = new ArrayList<String>();
    private Integer is_noti = 0; // 0 : app_version <= '3.2.6' , 1: app_version > '3.2.6' .
    private List<PushTargetInfo> target_info = new ArrayList<PushTargetInfo>();
    private String subject;
    private String body;
    private String code = "";
    private String sub_data = "";

    @JsonIgnore
    public void addTargetInfo(String mb_id, String wp_id, String token, String appVersion){
        if( StringUtils.isNotBlank(token)){
            target_info.add(new PushTargetInfo( mb_id, wp_id, token, appVersion) );
        }
    }

    @JsonIgnore
    public String toDataString(String prefix){
        try{
            return StringUtils.defaultIfEmpty(prefix, "") + JsonUtil.toStringJson(this, false);
        }
        catch(Exception e){
            e.printStackTrace();
            return StringUtils.defaultIfEmpty(prefix, "") ;
        }
    }

    @JsonIgnore
    public int targetInfoSize(){
        return target_info.size();
    }

    @JsonIgnore
    public void reconstructTargetInfo(List<PushTargetInfo> delimetedTarget){
        this.target_info = delimetedTarget;
        this.token.clear();
        for(PushTargetInfo targetInfo : delimetedTarget){
            this.token.add(targetInfo.getToken());
        }
    }

    @JsonIgnore
    public List<PushTargetInfo> getTargetInfoByNotiType(int notiType){
        List<PushTargetInfo> filteredTargetInfo = new ArrayList<PushTargetInfo>();
        for(PushTargetInfo targetInfo : target_info){
            if( targetInfo.getNotiType() != null && targetInfo.getNotiType() == notiType ){
                filteredTargetInfo.add(targetInfo);
            }
        }
        return filteredTargetInfo;
    }


}
