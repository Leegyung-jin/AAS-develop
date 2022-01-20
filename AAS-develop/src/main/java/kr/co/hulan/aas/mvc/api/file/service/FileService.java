package kr.co.hulan.aas.mvc.api.file.service;

import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.Storage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.FileHelper;
import kr.co.hulan.aas.config.properties.FileConfigurationProperties;
import kr.co.hulan.aas.mvc.api.file.dto.ImageInfo;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileService {

    @Autowired
    FileConfigurationProperties FileConfigurationProperties;

    public String getTempFilePath(){
        StringBuilder tempFilePath = new StringBuilder();
        tempFilePath.append(FileConfigurationProperties.getPath());
        tempFilePath.append(FileConfigurationProperties.getTempFilePath());
        return tempFilePath.toString();
    }

    public File getTempFile(String fileName){
        StringBuilder tempFilePath = new StringBuilder();
        tempFilePath.append(FileConfigurationProperties.getPath());
        tempFilePath.append(FileConfigurationProperties.getTempFilePath());
        if( !StringUtils.endsWith(tempFilePath, File.separator)){
            tempFilePath.append(File.separator);
        }
        tempFilePath.append(fileName);
        return new File(tempFilePath.toString());
    }

    public String getSafeFilePath(){
        StringBuilder memberFilePath = new StringBuilder();
        memberFilePath.append(FileConfigurationProperties.getPath());
        memberFilePath.append(FileConfigurationProperties.getSafeFilePath());
        return memberFilePath.toString();
    }

    public String getMemberFilePath(){
        StringBuilder memberFilePath = new StringBuilder();
        memberFilePath.append(FileConfigurationProperties.getPath());
        memberFilePath.append(FileConfigurationProperties.getMemberFilePath());
        return memberFilePath.toString();
    }

    public String getNoticeFilePath(){
        StringBuilder memberFilePath = new StringBuilder();
        memberFilePath.append(FileConfigurationProperties.getPath());
        memberFilePath.append(FileConfigurationProperties.getBoardFilePath());
        memberFilePath.append("notice");
        return memberFilePath.toString();
    }

    public String getWpBoardFilePath(){
        StringBuilder memberFilePath = new StringBuilder();
        memberFilePath.append(FileConfigurationProperties.getPath());
        memberFilePath.append(FileConfigurationProperties.getBoardFilePath());
        memberFilePath.append("wpboard");
        return memberFilePath.toString();
    }

    public String getWorkplaceFilePath( String wpId, boolean isAbsolutePath ){
        StringBuilder memberFilePath = new StringBuilder();
        if( isAbsolutePath ){
            memberFilePath.append(FileConfigurationProperties.getPath());
        }
        memberFilePath.append(String.format(FileConfigurationProperties.getWorkplaceFilePath(), wpId));
        return memberFilePath.toString();
    }

    public String getBuildingFilePath( long buildingNo, boolean isAbsolutePath ){
        StringBuilder buildingFilePath = new StringBuilder();
        if( isAbsolutePath ){
            buildingFilePath.append(FileConfigurationProperties.getPath());
        }
        buildingFilePath.append(String.format(FileConfigurationProperties.getBuildingFilePath(), buildingNo));
        return buildingFilePath.toString();
    }

    public String getUiComponentFilePath( String cmptId, boolean isAbsolutePath ){
        StringBuilder filePath = new StringBuilder();
        if( isAbsolutePath ){
            filePath.append(FileConfigurationProperties.getPath());
        }
        filePath.append(String.format(FileConfigurationProperties.getUiComponentFilePath(), cmptId));
        return filePath.toString();
    }

    public String getOfficeFilePath( long officeNo, boolean isAbsolutePath ){
        StringBuilder filePath = new StringBuilder();
        if( isAbsolutePath ){
            filePath.append(FileConfigurationProperties.getPath());
        }
        filePath.append(String.format(FileConfigurationProperties.getOfficeFilePath(), ""+officeNo));
        return filePath.toString();
    }

    public String getConCompanyFilePath( String ccId, boolean isAbsolutePath ){
        StringBuilder filePath = new StringBuilder();
        if( isAbsolutePath ){
            filePath.append(FileConfigurationProperties.getPath());
        }
        filePath.append(String.format(FileConfigurationProperties.getConCompanyFilePath(), ccId));
        return filePath.toString();
    }

    public String getImosNoticeFilePath( long imntNo, boolean isAbsolutePath ){
        StringBuilder filePath = new StringBuilder();
        if( isAbsolutePath ){
            filePath.append(FileConfigurationProperties.getPath());
        }
        filePath.append(String.format(FileConfigurationProperties.getImosNoticeFilePath(), ""+imntNo));
        return filePath.toString();
    }

    public long getTempFileSize(String tempFile){
        File file = new File(getTempFilePath()+tempFile);
        if( file.exists() && file.isFile()){
            return file.length();
        }
        return 0;
    }

    public ImageInfo getImageInfo(String tempFile){
        try
        {
            File file = new File(getTempFilePath()+tempFile);
            BufferedImage bi = ImageIO.read( file );
            return new ImageInfo(
                    bi.getWidth()
                    ,bi.getHeight()
                    ,bi.getType()
                    ,file.length()
            );
        } catch( Exception e ) {
            System.out.println("이미지 파일이 아닙니다.");
            return null;
        }
    }


    public UploadedFile uploadFile(MultipartFile uploadFile, String path){
        try {
            String savedName = FileHelper.makeFileName(uploadFile);
            File pathDir = new File (path);
            if ( !pathDir.exists() ){
                pathDir.mkdirs();
            }
            StringBuilder savedFilePathBuilder = new StringBuilder();
            savedFilePathBuilder.append(path);
            if( !StringUtils.endsWith(path, File.separator)){
                savedFilePathBuilder.append(File.separator);
            }
            savedFilePathBuilder.append(savedName);
            uploadFile.transferTo(new File(savedFilePathBuilder.toString()) );
            return new UploadedFile( savedName, uploadFile.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(BaseCode.ERR_ETC_EXCEPTION.code(), "["+uploadFile.getOriginalFilename()+"] 파일을 저장하는데 실패하였습니다.");
        }
    }

    public List<UploadedFile> uploadFiles(MultipartHttpServletRequest request, String savedPath){
        Iterator<String> iterator = request.getFileNames();
        List<UploadedFile> uploadFileList = new ArrayList<UploadedFile>();
        while (iterator.hasNext()) {
            String fileName = iterator.next();
            UploadedFile uploadedFile = uploadFile(
                    request.getFile(fileName),
                    savedPath
            );
            uploadFileList.add(uploadedFile);
        }
        return uploadFileList;
    }

    public List<UploadedFile> uploadFiles(List<MultipartFile> multipartFileList, String savedPath){
        List<UploadedFile> uploadFileList = new ArrayList<UploadedFile>();
        for( MultipartFile multipartFile :  multipartFileList){
            UploadedFile uploadedFile = uploadFile(
                    multipartFile,
                    savedPath
            );
            uploadFileList.add(uploadedFile);
        }
        return uploadFileList;
    }

    public void moveTempFile(String fileName, String targetDir) {
        try{
            File destDirFile = new File(targetDir);
            if( !destDirFile.exists()){
                FileUtils.forceMkdir(destDirFile);
            }
            File srcFile = getTempFile(fileName);

            StringBuilder targetFile = new StringBuilder();
            targetFile.append(targetDir);
            if( !StringUtils.endsWith(targetDir, File.separator)){
                targetFile.append(File.separator);
            }
            targetFile.append(fileName);
            File destFile = new File(targetFile.toString());
            FileUtils.moveFile(srcFile, destFile);
        }
        catch(Exception e){
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "["+fileName+"] 파일 처리 중 오류가 발생하였습니다.");
        }

    }


    public void copyTempFile(String fileName, String targetDir) {
        try{
            File destDirFile = new File(targetDir);
            if( !destDirFile.exists()){
                FileUtils.forceMkdir(destDirFile);
            }
            File srcFile = getTempFile(fileName);

            StringBuilder targetFile = new StringBuilder();
            targetFile.append(targetDir);
            if( !StringUtils.endsWith(targetDir, File.separator)){
                targetFile.append(File.separator);
            }
            targetFile.append(fileName);
            File destFile = new File(targetFile.toString());
            FileUtils.copyFile(srcFile, destFile);
        }
        catch(Exception e){
            throw new CommonException(BaseCode.ERR_DETAIL_EXCEPTION.code(), "["+fileName+"] 파일 처리 중 오류가 발생하였습니다.");
        }

    }

    public void deleteTempFiles(){
        // TODO
    }

    public void deleteFile(String fileAbolutePath ){

    }
}
