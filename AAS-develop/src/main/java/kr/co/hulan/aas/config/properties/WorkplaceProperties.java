package kr.co.hulan.aas.config.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix="workplace")
public class WorkplaceProperties {

  private List<Map<String,Object>> infos = new ArrayList<Map<String,Object>>();

  public String getMemo(String wpId){
    for( Map<String,Object> info : infos){
      String infosWpId = (String) info.get("wpId");
      if( StringUtils.equals(infosWpId, wpId )){
        return (String) info.get("memo");
      }
    }
    return "";
  }

}
