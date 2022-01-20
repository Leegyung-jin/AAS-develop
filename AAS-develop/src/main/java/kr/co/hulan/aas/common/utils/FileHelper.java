package kr.co.hulan.aas.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileHelper {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String getExtension(MultipartFile file)
            throws IllegalArgumentException {
        return getExtension(file, true);
    }

    public static String getExtension(MultipartFile file, boolean toLowerCase)
            throws IllegalArgumentException {

        if (file == null) {
            throw new IllegalArgumentException("업로드 대상 파일이 없음");
        }

        String fileName = file.getOriginalFilename();

        return getExtensionFromName(fileName, toLowerCase);
    }

    public static String getExtensionFromName(String fileName, boolean toLowerCase) {
        if (fileName == null || fileName.equals("")) {
            throw new IllegalArgumentException("파일명이 없음");
        }

        String[] fileNameInformation = fileName.split("\\.");
        String extension = fileNameInformation[fileNameInformation.length - 1];

        return (toLowerCase)? extension.toLowerCase(): extension;
    }

    public static String makeFileName(MultipartFile file){
        return makeFileName(getExtension(file, true), null, null);
    }

    public static String makeFileName(String extension, String prefix, String suffix) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        timeStamp += "_" + RandomStringUtils.randomAlphanumeric(10);

        StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix)
                    .append("_");
        }

        sb.append(timeStamp);

        if (suffix != null) {
            sb.append("_").append(suffix);
        }
        return sb.append(".").append(extension).toString();
    }


    public static boolean deleteDirectory(File path) {
        if(!path.exists()) {
            return false;
        }

        File[] files = path.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }

        return path.delete();
    }


}
