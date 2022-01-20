package kr.co.hulan.aas.mvc.service.push;

import kr.co.hulan.aas.common.utils.YamlPropertiesAccessor;
import kr.co.hulan.aas.config.properties.HttpClientProperties;
import kr.co.hulan.aas.mvc.api.push.controller.request.SendPushRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class PushService {

    private Logger logger = LoggerFactory.getLogger(PushService.class);

    @Autowired
    private HttpClientProperties httpClientProperties;

    @Autowired
    private CloseableHttpClient client;

    @Autowired
    @Qualifier("fixedExecutorService")
    private ExecutorService fixedExecutorService;

    public void sendPush(PushData obj){

        if( httpClientProperties.isSupport()){
            int max_size = 1000;
            int[] notiTypeArray = new int[]{0, 1};  // AppVersion 별 체크
            for( int notiType : notiTypeArray ){
                List<PushTargetInfo> filteredTargetInfo = obj.getTargetInfoByNotiType(notiType);
                if( filteredTargetInfo == null || filteredTargetInfo.size() == 0 ){
                    continue;
                }
                if( filteredTargetInfo.size() > max_size ){
                    for( int idx = filteredTargetInfo.size() ; idx > 0; idx -= max_size ){
                        List<PushTargetInfo> delimetedTokens = filteredTargetInfo.subList(filteredTargetInfo.size() - idx, Math.min( filteredTargetInfo.size() - idx + max_size , filteredTargetInfo.size() ));
                        if( delimetedTokens != null ){
                            PushData pushData = new PushData();
                            pushData.setSubject( obj.getSubject() );
                            pushData.setBody( obj.getBody() );
                            pushData.setCode( obj.getCode() );
                            pushData.setSub_data( obj.getSub_data() );
                            pushData.setIs_noti(notiType);
                            pushData.reconstructTargetInfo(delimetedTokens);

                            fixedExecutorService.submit(new PushSender(client, httpClientProperties.getPushRequestUrl(), pushData));
                        }
                        else {
                            logger.warn("PushService invalid length check"+(obj.targetInfoSize() - idx)+" ~ "+ Math.min( obj.targetInfoSize() - idx + max_size , obj.targetInfoSize()) +" / "+obj.targetInfoSize());
                        }
                    }

                }
                else {
                    PushData pushData = new PushData();
                    pushData.setSubject( obj.getSubject() );
                    pushData.setBody( obj.getBody() );
                    pushData.setCode( obj.getCode() );
                    pushData.setSub_data( obj.getSub_data() );
                    pushData.setIs_noti(notiType);
                    pushData.reconstructTargetInfo(filteredTargetInfo);

                    fixedExecutorService.submit(new PushSender(client, httpClientProperties.getPushRequestUrl(), pushData));
                }
            }
        }
        else {
            logger.warn("PushService is not supported."+obj.toString());
        }
    }


}
