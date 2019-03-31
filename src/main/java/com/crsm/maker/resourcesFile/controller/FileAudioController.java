package com.crsm.maker.resourcesFile.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crsm.maker.base.BaseController;
import com.crsm.maker.resourcesFile.entity.FileAudio;
import com.crsm.maker.resourcesFile.entity.SysResource;
import com.crsm.maker.resourcesFile.service.IFileAudioService;
import com.crsm.maker.resourcesFile.service.ISystemResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 音乐表 前端控制器
 * </p>
 *
 * @author Ccr
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/fileAudio")
public class FileAudioController extends BaseController {

    @Autowired
    IFileAudioService iFileAudioService;

    @Autowired
    ISystemResourceService iSystemResourceService;


    /**
     * 添加音乐
     */
    @PostMapping("/addMusic")
    public String addMusic(@RequestBody FileAudio data) {
        data.setAudioCreate(LocalDateTime.now());
        System.out.println(data.toString());
        boolean result=iFileAudioService.save(data);
        if(result){
            return success();
        }else{
            return fail();
        }
    }

    /**
     * 获取音乐信息
     * @return
     */
    @GetMapping("/getAllMusicInfo")
    public String getAllMusicInfo(){
        List list=iFileAudioService.getAllAudioInfo();
        return success(list);
    }

    /**
     * 获取所有未添加的lrc文件信息
     * @return
     */
    @GetMapping("/getAllLrcFileInfo")
    public String getAllLrcFileInfo() {
        List list=iSystemResourceService.list(new QueryWrapper<SysResource>()
                .likeLeft("res_name", ".lrc")
                .notInSql("id", "SELECT audio_lrc_id FROM file_audio"));
        return success(list);
    }

    /**
     *
     * @return
     */
    @GetMapping("/getAllAudioFileInfo")
    public String getAllAudioFileInfo() {
        List list=iSystemResourceService.list(new QueryWrapper<SysResource>()
                .eq("res_type", "0")
                .notInSql("id", "SELECT `resource_id` FROM file_audio"));
        return success(list);
    }


}
