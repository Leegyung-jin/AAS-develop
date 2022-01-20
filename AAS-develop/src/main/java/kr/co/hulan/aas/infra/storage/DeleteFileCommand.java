package kr.co.hulan.aas.infra.storage;

import java.util.ArrayList;
import java.util.List;

public class DeleteFileCommand {

    private List<String> fullFilePathList;

    public DeleteFileCommand(){
        this.fullFilePathList = new ArrayList<String>();
    }
    public DeleteFileCommand(String fullFilePath){
        this();
        addFilePath(fullFilePath);
    }

    public DeleteFileCommand(List<String> fullFilePathList){
        this.fullFilePathList = fullFilePathList;
    }


    public void addFilePath(String fullFilePath){
        this.fullFilePathList.add(fullFilePath);
    }

    public String[] getDeleteFilePaths(){
        return this.fullFilePathList.toArray(new String[0]);
    }

    public boolean existsDeleteFiles(){
        return fullFilePathList != null && fullFilePathList.size() > 0;
    }
}
