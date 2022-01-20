package kr.co.hulan.aas.infra;

import kr.co.hulan.aas.infra.storage.DeleteFileCommand;
import kr.co.hulan.aas.infra.storage.DeleteFileWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;

@Component
public class StorageClient {

    private Logger logger = LoggerFactory.getLogger(StorageClient.class);

    @Autowired
    @Qualifier("fixedExecutorService")
    private ExecutorService fixedExecutorService;

    @PostConstruct
    protected void startUp(){
        logger.info(this.getClass().getSimpleName()+"|START UP");
    }

    @PreDestroy
    protected void shutDown(){
        logger.info(this.getClass().getSimpleName()+"|SHUT DOWN");
    }


    public void requestDeleteFiles(DeleteFileCommand command){
        if( command == null ){
            logger.error(this.getClass().getSimpleName()+"|requestDeleteFiles|Null Command Error");
            return;
        }


        LocalTime start = LocalTime.now();
        try{
            fixedExecutorService.submit(new DeleteFileWorker(command));
        }
        catch(Exception e){
            logger.error(this.getClass().getSimpleName()+"|fixedExecutorService|Error", e);
        }
        finally{
            Duration duration = Duration.between(start, LocalTime.now());
            if( duration.getSeconds() > 1 ){
                logger.warn( this.getClass().getSimpleName()+"|fixedExecutorService|Long Elapsed Time." + duration.getSeconds());
            }
        }
    }
}
