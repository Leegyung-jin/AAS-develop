package kr.co.hulan.aas.infra.storage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

public class DeleteFileWorker implements Runnable {

    private Logger logger = LoggerFactory.getLogger(DeleteFileWorker.class);

    private DeleteFileCommand command;

    public DeleteFileWorker(DeleteFileCommand command){
        this.command = command;
    }

    @Override
    public void run() {
        LocalTime start = LocalTime.now();
        try {
            if( command != null && command.getDeleteFilePaths() != null && command.getDeleteFilePaths().length != 0   ){
                for(String filePath : command.getDeleteFilePaths()){
                    try {
                        if(StringUtils.isBlank(filePath)){
                            continue;
                        }

                        File fileToDelete = FileUtils.getFile(filePath);
                        if( !fileToDelete.exists()){
                            logger.warn(this.getClass().getSimpleName()+"|DeleteFileWorker|400|file not found. ["+filePath+"]");
                            continue;
                        }
                        else if( !fileToDelete.isFile() ){
                            logger.warn(this.getClass().getSimpleName()+"|DeleteFileWorker|400|is not file. ["+filePath+"]");
                            continue;
                        }
                        FileUtils.touch(fileToDelete);

                        if( !FileUtils.deleteQuietly(fileToDelete) ){
                            logger.warn(this.getClass().getSimpleName()+"|DeleteFileWorker|500|Cannt delete file ["+filePath+"]");
                        }
                        else if( logger.isDebugEnabled() ){
                            logger.debug(this.getClass().getSimpleName()+"|DeleteFileWorker|200|Delete file["+filePath+"]");
                        }
                    } catch (IOException e) {
                        logger.warn(this.getClass().getSimpleName()+"|DeleteFileWorker|500|IOException OCCURED. ["+filePath+"]");
                    }
                }
            }
            else {
                logger.warn(this.getClass().getSimpleName()+"|DeleteFileWorker|404|DeleteFileCommand not exists.");
            }
        } catch (Exception e){
            logger.error(this.getClass().getSimpleName()+"|DeleteFileWorker|500|INTERNAL SERVER ERROR OCCURED", e);
        } finally{
            Duration duration = Duration.between(start, LocalTime.now());
            if( duration.getSeconds() > 1 ){
                logger.warn( "DeleteFileWorker|Long Elapsed Time|" + duration.getSeconds());
            }
        }
    }
}
