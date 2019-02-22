package com.crsm.maker.resourcesFile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.base.ResultStatusCodeEnum;
import com.crsm.maker.resourcesFile.entity.SysResource;
import com.crsm.maker.resourcesFile.service.ISystemResourceService;
import com.crsm.maker.user.entity.SysUser;
import com.crsm.maker.user.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

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
@RequestMapping("/file")
public class SystemResourceController extends BaseController {

    @Autowired
    ISystemResourceService iSystemResourceService;

    @Autowired
    ISysUserService iSysUserService;

    /**
     * 文件上传（单个
     * @param
     * @param file
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/multipartFile")
    public Object multiUplod(@RequestParam("file")MultipartFile file)throws IOException{
        SysUser  user=iSysUserService.getOne(new QueryWrapper<SysUser>().eq("usr_name","Ccr"));
        log.info("上传人员：{}",user.getUsrName());
        if(file.isEmpty()){
            log.warn("未获取文件");
            return "文件上传失败";
        }
        String upuserName="upFile";
        String fileName=file.getOriginalFilename();
        int isExist=iSystemResourceService.count(new QueryWrapper<SysResource>().eq("res_name",fileName));
        if(isExist>0){
            return fail(ResultStatusCodeEnum.REPEAT_DATA_ERROR);
        }
        String filePath=upuserName+"/";
        File fileData=new File(filePath);
        if(!fileData.exists()){
            fileData.mkdirs();
        }
        filePath=filePath+fileName;
        SysResource sResource=new SysResource();
        sResource.setResName(fileName);
        sResource.setResType(1);
        sResource.setResCreatetime(LocalDateTime.now());
        sResource.setResIsstop(0);
        sResource.setUserId(user.getId());
        sResource.setResPath(filePath);
        iSystemResourceService.save(sResource);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(filePath);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            log.error("文件上传失败····{}",e);
            e.printStackTrace();
            return fail();
        }
        log.info("文件上传成功，文件名：{}",fileName);
        return success();
    }


    @RequestMapping(value = "getResourceFileInfo",method = RequestMethod.GET)
    public String getResourceFileInfo(){
        Page<SysResource> page=new Page<>(1,10);
        SysResource sysResource=new SysResource();
        IPage<SysResource> iPage= iSystemResourceService.selectPageVo(page,sysResource);
        return success(iPage);
    }
}
