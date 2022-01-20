package kr.co.hulan.aas.mvc.api.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.api.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@Api(tags = "파일 관리")
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation(value = "임시 파일 업로드(단일 파일)", notes = "임시 파일 업로드(단일 파일) 제공한다.", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("uploadFile")
    public DefaultHttpRes<UploadedFile> uploadFiles(
            @RequestParam("file") MultipartFile file) {
        return new DefaultHttpRes<UploadedFile>(BaseCode.SUCCESS, fileService.uploadFile(file, fileService.getTempFilePath()));
    }

    @ApiOperation(value = "임시 파일 업로드", notes = "임시 파일 업로드 제공한다.", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("uploadMultipartFiles")
    public DefaultHttpRes<List<UploadedFile>> uploadMultipartFiles(
            MultipartHttpServletRequest request) {
        return new DefaultHttpRes<List<UploadedFile>>(BaseCode.SUCCESS, fileService.uploadFiles(request, fileService.getTempFilePath()));
    }

    @ApiOperation(value = "임시 파일 업로드2", notes = "임시 파일 업로드 제공한다.", produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("uploadFiles")
    public DefaultHttpRes<List<UploadedFile>> uploadFiles(
            @RequestParam("file") List<MultipartFile> file) {
        return new DefaultHttpRes<List<UploadedFile>>(BaseCode.SUCCESS, fileService.uploadFiles(file, fileService.getTempFilePath()));
    }


}
