package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.config.properties.FileConfigurationProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

public enum Storage {
    LOCAL_STORAGE(0, "localStorage" )
    ;

    private int code;
    private String name;
    private String path;
    private String downloadUrlBase;
    Storage(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public String getPath(){
        return path;
    }
    public String getDownloadUrlBase(){
        return downloadUrlBase;
    }
    public static Storage get(int code){
        for(Storage item : values()){
            if(code == item.getCode()){
                return item;
            }
        }
        return null;
    }
    public static Storage getByName(String name){
        for(Storage item : values()){
            if( StringUtils.equals( name, item.getName())){
                return item;
            }
        }
        return null;
    }

    private void initStorageConfig(FileConfigurationProperties config){
        this.downloadUrlBase = config.getDownload();
        this.path = config.getPath();
    }

    @Component
    public static class StorageConfigurationInjector {
        @Autowired
        private FileConfigurationProperties fileConfigurationProperties;

        @PostConstruct
        public void postConstruct() {
            for (Storage rt : EnumSet.allOf(Storage.class)){
                rt.initStorageConfig(fileConfigurationProperties);
            }
        }
    }
}
