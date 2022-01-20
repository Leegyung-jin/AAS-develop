package kr.co.hulan.aas.mvc.service.push;

import kr.co.hulan.aas.common.utils.JsonUtil;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class PushSender implements Runnable {

    private Logger resultLogger = LoggerFactory.getLogger(PushSender.class.getName()+".RESULT");
    private Logger logger = LoggerFactory.getLogger(PushSender.class);

    private HttpClient client;
    private String requestUrl;
    private PushData data;


    private HttpResponse response;

    public PushSender(HttpClient client, String requestUrl, PushData data){
        this.requestUrl = requestUrl;
        this.client = client;
        this.data = data;
    }

    @Override
    public void run() {
        LocalTime start = LocalTime.now();

        try {
            HttpPost postRequest = new HttpPost(requestUrl); //POST 메소드 URL 새성

            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Content-Type", "application/json;charset=UTF-8");

            String postData = JsonUtil.toStringJson(data);
            postRequest.setEntity(new StringEntity(postData, Consts.UTF_8)); //json 메시지 입력

            logger.debug("PUSH BODY "+requestUrl+"\n"+postData);
            // checkCallAbort(postRequest);

            response = client.execute(postRequest);

            if( response != null && response.getStatusLine() != null ){
                if( response.getStatusLine().getStatusCode() == HttpStatus.OK.value()){
                    logger.debug(this.getClass().getSimpleName()+"|sendEvent|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase());
                    resultLogger.info( data.toDataString("PUSH_DATA|SUCCESS|"+response.getStatusLine().getStatusCode()+"|" ) );
                }
                else {
                    logger.warn(this.getClass().getSimpleName()+"|sendEvent|"+response.getStatusLine().getStatusCode()+"|"+response.getStatusLine().getReasonPhrase()+"|"+ EntityUtils.toString(response.getEntity()));
                    resultLogger.info( data.toDataString("PUSH_DATA|ERROR_RES|"+response.getStatusLine().getStatusCode()+"|" ) );
                }
            }
            else {
                logger.error(this.getClass().getSimpleName()+"|sendEvent|500|INVALID RESPONSE");
                resultLogger.info( data.toDataString("PUSH_DATA|ERROR_RES|999|" ) );
            }
        } catch (Exception e){
            logger.error(this.getClass().getSimpleName()+"|sendEvent|500|INTERNAL SERVER ERROR OCCURED", e);
            resultLogger.info( data.toDataString("PUSH_DATA|ERROR_SENDER|999|" ) );
        } finally{
            HttpClientUtils.closeQuietly(response);
            Duration duration = Duration.between(start, LocalTime.now());
            if( duration.getSeconds() > 1 ){
                logger.warn( "PushSender|Long Elapsed Time|" + duration.getSeconds());
            }
        }
    }

    private void checkCallAbort(HttpPost postRequest){
        int hardTimeout = 6; // seconds
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                if (postRequest != null && response == null) {
                    postRequest.abort();
                    logger.warn( "PushSender|Not responsed|Aborted");
                }
            }
        }, hardTimeout * 1000);
    }

}
