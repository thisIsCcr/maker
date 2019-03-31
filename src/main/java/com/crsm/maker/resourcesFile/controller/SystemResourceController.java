package com.crsm.maker.resourcesFile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.base.ResultStatusCodeEnum;
import com.crsm.maker.resourcesFile.entity.FileImg;
import com.crsm.maker.resourcesFile.entity.SysResource;
import com.crsm.maker.resourcesFile.service.IFileImgService;
import com.crsm.maker.resourcesFile.service.ISystemResourceService;
import com.crsm.maker.socketService.webSocket.Global;
import com.crsm.maker.user.entity.SysUser;
import com.crsm.maker.user.service.ISysUserService;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
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

    @Autowired
    IFileImgService iFileImgService;


    private final String upuserName = "/home/resource/systemResource";

    /**
     * 文件上传（单个
     *
     * @param
     * @param file
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/multipartFile")
    public Object multiUplod(@RequestParam("file") MultipartFile file) throws IOException {
        SysUser user = currentUserInfo();
        if (!islogin()) {
            user = iSysUserService.getOne(new QueryWrapper<SysUser>().eq("usr_name", "visitor"));
        }
        log.info("上传人员：{}", user.getUsrName());
        if (file.isEmpty()) {
            log.warn("未获取文件");
            return fail();
        }
        log.info("文件大小：{}", file.getSize());
        String fileName = file.getOriginalFilename();
        int isExist = iSystemResourceService.count(new QueryWrapper<SysResource>().eq("res_name", fileName));
        if (isExist > 0) return fail(ResultStatusCodeEnum.REPEAT_DATA_ERROR);
        String filePath = upuserName + "/" + user.getUsrName();
        StringBuilder fileTypePath = new StringBuilder("/");
        FileImg fileImg = null;
        Integer resType = 4;
        log.info("文件类型：{}", file.getContentType());
        switch (file.getContentType()) {
            //文本文件
            case MediaType.TEXT_PLAIN_VALUE:
                fileTypePath.append("text");
                resType = 3;
                break;
            //图片类型
            case MediaType.IMAGE_PNG_VALUE:
            case MediaType.IMAGE_GIF_VALUE:
            case MediaType.IMAGE_JPEG_VALUE:
                resType = 2;
                fileTypePath.append("img");
                BufferedImage image = ImageIO.read(file.getInputStream());
                fileImg = new FileImg();
                fileImg.setImgHeight(image.getHeight());
                fileImg.setImgWidth(image.getWidth());
                break;
            case "audio/mp3":
                fileTypePath.append("mp3");
                resType = 0;
                break;
            case "video/mp4":
                resType = 1;
                fileTypePath.append("mp4");
                break;
            default:
                fileTypePath.append("other");
                break;
        }
        fileTypePath.append("/");
        filePath += fileTypePath.toString();
        File fileData = new File(filePath);
        fileData.setWritable(true,false);
        if (!fileData.exists()) fileData.mkdirs();
        filePath += fileName;
        System.out.println(fileData.getAbsolutePath());
        //读取输出流
        @Cleanup
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        //获取输入流
        @Cleanup
        BufferedInputStream stream = new BufferedInputStream(file.getInputStream());
        byte[] buff = new byte[4096];
        int offset = 0;
        //将数据读取至 buff
        while ((offset = stream.read(buff)) != -1) {
            fileOutputStream.write(buff);
        }
        fileOutputStream.flush();
        SysResource sResource = new SysResource();
        sResource.setResName(fileName);
        sResource.setResType(resType);
        sResource.setResCreatetime(LocalDateTime.now());
        sResource.setResIsstop(0);
        sResource.setUserId(user.getId());
        sResource.setResPath(filePath.substring(14));
        iSystemResourceService.save(sResource);
        if (fileImg != null) {
            fileImg.setResurceId(sResource.getId());
            iFileImgService.save(fileImg);
        }

        TextWebSocketFrame content = new TextWebSocketFrame(user.getUsrName() + "上传了文件：【" + fileName + "】");
        if (Global.group.isEmpty()) {
            System.out.println("No Online user");
        }else{
            Global.group.writeAndFlush(content);
        }

        log.info("文件上传成功，文件名：{}", fileName);
        return success();
    }


    @RequestMapping(value = "getResourceFileInfo", method = RequestMethod.GET)
    public String getResourceFileInfo() {
        Page<SysResource> page = new Page<>(1, 40);
        SysResource sysResource = new SysResource();
        IPage<SysResource> iPage = iSystemResourceService.selectPageVo(page, sysResource);
        return success(iPage);
    }
}
