package com.crsm.maker.resourcesFile.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>
 * 系统资源表 前端控制器
 * </p>
 *
 * @author Ccr
 * @since 2018-12-25
 */
@Slf4j
@RestController
public class SystemResourceController {


    @RequestMapping("/multipartFile")
    public Object multiUplod(HttpServletRequest request,@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            log.warn("未获取文件");
            return "文件上传失败";
        }
        String upuserName=request.getSession().getServletContext().getServletContextName();
        String fileName=file.getOriginalFilename();
        String filePath="D:/文件上传测试/";
        File fileData=new File(filePath);
        if(!fileData.exists()){
            fileData.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(filePath+fileName);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            log.error("文件上传失败····{}",e);
            e.printStackTrace();
            return "文件上传失败";
        }
        return "文件上传成功";
    }
}
